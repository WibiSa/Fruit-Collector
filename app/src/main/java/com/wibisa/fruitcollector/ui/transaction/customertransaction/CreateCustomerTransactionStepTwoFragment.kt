package com.wibisa.fruitcollector.ui.transaction.customertransaction

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.datepicker.*
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.util.LocalResourceData
import com.wibisa.fruitcollector.databinding.FragmentCreateCustomerTransactionStepTwoBinding
import com.wibisa.fruitcollector.databinding.ItemTransCustomerCommodityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateCustomerTransactionStepTwoFragment : Fragment() {

    private lateinit var binding: FragmentCreateCustomerTransactionStepTwoBinding
    private lateinit var partialBinding: ItemTransCustomerCommodityBinding
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var localResourceData: LocalResourceData
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            FragmentCreateCustomerTransactionStepTwoBinding.inflate(inflater, container, false)
        partialBinding = binding.partialFruitCommodity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()
    }

    private fun componentUiSetup() {

        localResourceData = LocalResourceData(requireContext())

        binding.appbar.setNavigationOnClickListener { mainFlowNavController?.popBackStack() }

        val dummyFruitComm = localResourceData.dummyTransFarmer[0]

        partialBinding.apply {
            val fruitAndGrade = getString(
                R.string.fruit_name_and_grade,
                dummyFruitComm.commodityName,
                dummyFruitComm.grade
            )
            tvFruitNameAndGrade.text = fruitAndGrade
            tvFarmerName.text = dummyFruitComm.farmerName
            tvHarvestDate.text =
                getString(R.string.harvest_date_with_date, dummyFruitComm.harvestDate)
            tvStock.text = getString(R.string.stock_with_value, dummyFruitComm.stock)
            tvPricePerKg.text = getString(R.string.price_per_kg, dummyFruitComm.pricePerKg)
        }

        tfDeliveryDateSetup()

        binding.tvPriceTotal.text = "Rp 925.000"

        binding.btnSave.setOnClickListener {
            // TODO: input validation!
            CoroutineScope(Dispatchers.Main).launch {
                binding.loadingIndicator.show()
                delay(1500)
                binding.loadingIndicator.hide()
                save()
            }
        }
    }

    private fun save() {
        // TODO: save data farmer to server
        mainFlowNavController?.popBackStack(R.id.homeScreen, false)
    }

    private fun tfDeliveryDateSetup() {
        binding.tfDeliveryDate.apply {
            inputType = InputType.TYPE_NULL
            setOnClickListener { datePicker("Tanggal pengiriman") }
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) datePicker("Tanggal pengiriman")
            }
        }
    }

    private fun datePicker(title: String) {
        activity?.let { activity ->
            val today = MaterialDatePicker.todayInUtcMilliseconds()
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = today
            calendar.roll(Calendar.MONTH, 1)
            calendar.add(Calendar.DAY_OF_MONTH, 30)
            val lowerBound = calendar.timeInMillis

            val validators: ArrayList<CalendarConstraints.DateValidator> = arrayListOf()
            validators.add(DateValidatorPointBackward.before(lowerBound))
            validators.add(DateValidatorPointForward.now())

            val constraintsBuilder = CalendarConstraints.Builder()
                .setValidator(CompositeDateValidator.allOf(validators))

            val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraintsBuilder.build())
                .setTitleText(title)

            datePicker = datePickerBuilder.build()
            datePicker.show(activity.supportFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { dateSelected ->
                val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val date = sdf.format(dateSelected)

                binding.tfDeliveryDate.setText(date)

            }
        }
    }
}
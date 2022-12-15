package com.wibisa.fruitcollector.ui.transaction.customertransaction

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.datepicker.*
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.domain.model.InputAddCustomerTransaction
import com.wibisa.fruitcollector.core.util.*
import com.wibisa.fruitcollector.databinding.FragmentCreateCustomerTransactionStepTwoBinding
import com.wibisa.fruitcollector.databinding.ItemCreateCustomerTransactionBinding
import com.wibisa.fruitcollector.viewmodel.CreateCustomerTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateCustomerTransactionStepTwoFragment : Fragment() {

    private lateinit var binding: FragmentCreateCustomerTransactionStepTwoBinding
    private lateinit var partialBinding: ItemCreateCustomerTransactionBinding
    private lateinit var datePicker: MaterialDatePicker<Long>
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val viewModel: CreateCustomerTransactionViewModel by hiltNavGraphViewModels(R.id.createTransactionWithCustomer)

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

        binding.appbar.setNavigationOnClickListener { mainFlowNavController?.popBackStack() }

        bindSelectedFarmerTransaction()

        tfDeliveryDateSetup()

        binding.btnSave.setOnClickListener {
            val isSelectedFarmerTransactionValidate =
                viewModel.selectedFarmerTransaction.value != null
            val isFormInputValidate =
                binding.tfQuantity.isNotNullOrEmpty(getString(R.string.required)) and
                        binding.tfPrice.isNotNullOrEmpty(getString(R.string.required)) and
                        binding.tfBuyerName.isNotNullOrEmpty(getString(R.string.required)) and
                        binding.tfPhone.isNotNullOrEmpty(getString(R.string.required)) and
                        binding.tfAddress.isNotNullOrEmpty(getString(R.string.required)) and
                        binding.tfDeliveryDate.isNotNullOrEmpty(getString(R.string.required)) and
                        binding.tfShippingPrice.isNotNullOrEmpty(getString(R.string.required))

            hideKeyboard()

            if (isSelectedFarmerTransactionValidate && isFormInputValidate)
                observeUserPreferencesForCreateCustomerTransaction()
        }
    }

    private fun bindSelectedFarmerTransaction() {
        viewModel.selectedFarmerTransaction.observe(viewLifecycleOwner) { farmerTransaction ->
            partialBinding.apply {
                val fruitAndGrade = getString(
                    R.string.fruit_name_and_grade,
                    farmerTransaction.commodityName,
                    farmerTransaction.grade
                )
                tvFruitNameAndGrade.text = fruitAndGrade
                tvFarmerName.text = farmerTransaction.farmerName
                tvHarvestDate.text =
                    getString(R.string.harvest_date_with_date, farmerTransaction.harvestDate)
                tvStock.text = getString(R.string.stock_with_value, farmerTransaction.stock)
                val price = farmerTransaction.pricePerKg.toString().rupiahCurrencyFormat()
                tvPricePerKg.text = getString(R.string.price_per_kg_from_farmer_with_value, price)
            }
        }
    }

    private fun observeUserPreferencesForCreateCustomerTransaction() {
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            createCustomerTransaction(it.token)
        }
    }

    private fun createCustomerTransaction(token: String) {
        val quantity = binding.tfQuantity.text.toString().toInt()
        val price = binding.tfPrice.text.toString().toInt()

        // buyer data
        val name = binding.tfBuyerName.text.toString()
        val phone = binding.tfPhone.text.toString()
        val address = binding.tfAddress.text.toString()
        val deliveryDate = binding.tfDeliveryDate.text.toString()
        val shippingPrice = binding.tfShippingPrice.text.toString().toInt()
        val totalPrice = (quantity * price).plus(shippingPrice)

        val inputAddCustomerTransaction = InputAddCustomerTransaction(
            farmerTransactionId = viewModel.selectedFarmerTransaction.value!!.id,
            quantity = quantity,
            price = price,
            buyerName = name,
            phone = phone,
            address = address,
            shippingDate = deliveryDate,
            shippingPrice = shippingPrice,
            totalPrice = totalPrice
        )

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.createCustomerTransaction(token, inputAddCustomerTransaction)
                    .collect { ui ->
                        when (ui) {
                            is ApiResult.Success -> {
                                binding.loadingIndicator.hide()
                                requireContext().showToast(ui.data)
                                mainFlowNavController?.popBackStack(R.id.homeScreen, false)
                            }
                            is ApiResult.Loading -> {
                                binding.loadingIndicator.show()
                            }
                            is ApiResult.Error -> {
                                binding.loadingIndicator.hide()
                                requireContext().showToast(ui.message)
                            }
                            else -> {}
                        }
                    }
            }
        }
    }

    private fun tfDeliveryDateSetup() {
        binding.tfDeliveryDate.apply {
            inputType = InputType.TYPE_NULL
            setOnClickListener { datePicker() }
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) datePicker()
            }
        }
    }

    private fun datePicker() {
        activity?.let { activity ->
            val today = MaterialDatePicker.todayInUtcMilliseconds()
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = today
            calendar.roll(Calendar.MONTH, 1)
            calendar.add(Calendar.DAY_OF_MONTH, 30)
            val lowerBound = calendar.timeInMillis

            val validators: ArrayList<CalendarConstraints.DateValidator> = arrayListOf()
//            validators.add(DateValidatorPointBackward.before(lowerBound))
            validators.add(DateValidatorPointForward.now())

            val constraintsBuilder = CalendarConstraints.Builder()
                .setValidator(CompositeDateValidator.allOf(validators))

            val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraintsBuilder.build())
                .setTitleText("Tanggal pengiriman")

            datePicker = datePickerBuilder.build()
            datePicker.show(activity.supportFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { dateSelected ->
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = sdf.format(dateSelected)

                binding.tfDeliveryDate.setText(date)
            }
        }
    }
}
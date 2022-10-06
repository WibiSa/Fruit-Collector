package com.wibisa.fruitcollector.ui.commodity

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.datepicker.*
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.util.LocalResourceData
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentCommodityDetailsBinding
import java.text.SimpleDateFormat
import java.util.*

class CommodityDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCommodityDetailsBinding
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var localResourceData: LocalResourceData
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCommodityDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localResourceData = LocalResourceData(requireContext())

        componentUiSetup()
    }

    private fun componentUiSetup() {

        binding.apply {
            tvFarmerName.text = localResourceData.dummyFarmers[0].name
            tvFruitName.text = localResourceData.dummyFruits[0].name
        }

        tfTreeBlossomDateSetup()

        tfHarvestDateSetup()

        tfFruitGradeExposedDropdownSetup()

        binding.btnBack.setOnClickListener { mainFlowNavController?.popBackStack() }

        binding.btnSave.setOnClickListener { editCommodity() }

        binding.btnDelete.setOnClickListener { deleteCommodity() }

        binding.btnValidation.setOnClickListener { validationCommodity() }
    }

    private fun tfHarvestDateSetup() {
        binding.tfHarvestDate.apply {
            inputType = InputType.TYPE_NULL
            setOnClickListener { datePicker(getString(R.string.harvest_date)) }
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) datePicker(getString(R.string.harvest_date))
            }
        }
    }

    private fun tfTreeBlossomDateSetup() {
        binding.tfTreeBlossom.apply {
            inputType = InputType.TYPE_NULL
            setOnClickListener { datePicker(getString(R.string.tree_blossom_date)) }
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) datePicker(getString(R.string.tree_blossom_date))
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

                // dummy method
                if (title == getString(R.string.tree_blossom_date))
                    binding.tfTreeBlossom.setText(date)
                else
                    binding.tfHarvestDate.setText(date)
            }
        }
    }

    private fun tfFruitGradeExposedDropdownSetup() {
        val items = localResourceData.dummyFruitGrades
        val adapter = ArrayAdapter(requireContext(), R.layout.item_exposed_dropdown_menu, items)
        (binding.tfFruitGrade as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun validationCommodity() {
        requireContext().showToast("Fitur Dalam Pengembangan.")
    }

    private fun deleteCommodity() {
        requireContext().showToast("Fitur Dalam Pengembangan.")
    }

    private fun editCommodity() {
        requireContext().showToast("Fitur Dalam Pengembangan.")
    }
}
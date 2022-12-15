package com.wibisa.fruitcollector.ui.commodity

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.*
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.domain.model.Commodity
import com.wibisa.fruitcollector.core.domain.model.InputEditCommodity
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.LocalResourceData
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentCommodityDetailsBinding
import com.wibisa.fruitcollector.viewmodel.CommodityDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CommodityDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCommodityDetailsBinding
    private lateinit var datePicker: MaterialDatePicker<Long>
    private lateinit var localResourceData: LocalResourceData
    private val viewModel: CommodityDetailsViewModel by viewModels()
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val args: CommodityDetailsFragmentArgs by navArgs()

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

        observeEditCommodityUiState()

        observeVerifyCommodityUiState()

        observeDeleteCommodityUiState()
    }

    private fun componentUiSetup() {

        bindCommodity(args.commodity)

        tfTreeBlossomDateSetup()

        tfHarvestDateSetup()

        tfFruitGradeExposedDropdownSetup()

        binding.btnBack.setOnClickListener { mainFlowNavController?.popBackStack() }

        binding.btnSave.setOnClickListener {
            observeUserPreferencesForEditCommodity()
        }

        binding.btnDelete.isEnabled = args.commodity.isValid != 1
        binding.btnSave.isEnabled = args.commodity.isValid != 1

        binding.btnDelete.setOnClickListener {
            observeUserPreferencesForDeleteCommodity()
        }

        val isValid = args.commodity.blossomsTreeDate.isNullOrEmpty() or
                args.commodity.harvestDate.isNullOrEmpty() or
                args.commodity.grade.isNullOrEmpty() or
                (args.commodity.stock!! == 0)
        binding.btnValidation.isEnabled = !isValid
        binding.btnValidation.setOnClickListener {
            if (args.commodity.isValid != 1)
                observeUserPreferencesForValidationCommodity()
            else
                requireContext().showToast("Anda telah melakukan validasi sebelumnya.")
        }
    }

    private fun bindCommodity(commodity: Commodity) {
        binding.apply {
            tvFarmerName.text = commodity.farmerName
            tvFruitName.text = commodity.commodityName

            tfTreeBlossom.setText(if (commodity.blossomsTreeDate.isNullOrEmpty()) "" else commodity.blossomsTreeDate)
            tfHarvestDate.setText(if (commodity.harvestDate.isNullOrEmpty()) "" else commodity.harvestDate)
            tfFruitGrade.setText(if (commodity.grade.isNullOrEmpty()) "" else commodity.grade)
            commodity.stock?.let { tfProductionResult.setText(it.toString()) }
        }
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
//            validators.add(DateValidatorPointBackward.before(lowerBound))12
            validators.add(DateValidatorPointForward.now())

            val constraintsBuilder = CalendarConstraints.Builder()
                .setValidator(CompositeDateValidator.allOf(validators))

            val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraintsBuilder.build())
                .setTitleText(title)

            datePicker = datePickerBuilder.build()
            datePicker.show(activity.supportFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { dateSelected ->
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
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

    private fun observeUserPreferencesForValidationCommodity() {
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            viewModel.verifyCommodity(it.token, args.commodity.id)
        }
    }

    private fun observeVerifyCommodityUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.verifyCommodityUiState.collect { ui ->
                    when (ui) {
                        is ApiResult.Success -> {
                            binding.loadingIndicator.hide()
                            requireContext().showToast("Berhasil divalidasi.")
                            mainFlowNavController?.popBackStack()
                            viewModel.verifyCommodityCompleted()
                        }
                        is ApiResult.Loading -> {
                            binding.loadingIndicator.show()
                        }
                        is ApiResult.Error -> {
                            binding.loadingIndicator.hide()
                            // TODO: code error handling here!
                            requireContext().showToast(
                                getString(
                                    R.string.something_went_wrong_with_message,
                                    ui.message
                                )
                            )
                            viewModel.verifyCommodityCompleted()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun observeUserPreferencesForDeleteCommodity() {
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            viewModel.deleteCommodity(it.token, args.commodity.id)
        }
    }

    private fun observeDeleteCommodityUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.deleteCommodityUiState.collect { ui ->
                    when (ui) {
                        is ApiResult.Success -> {
                            binding.loadingIndicator.hide()
                            requireContext().showToast("Berhasil dihapus.")
                            mainFlowNavController?.popBackStack()
                            viewModel.deleteCommodityCompleted()
                        }
                        is ApiResult.Loading -> {
                            binding.loadingIndicator.show()
                        }
                        is ApiResult.Error -> {
                            binding.loadingIndicator.hide()
                            // TODO: code error handling here!
                            requireContext().showToast(
                                getString(
                                    R.string.something_went_wrong_with_message,
                                    ui.message
                                )
                            )
                            viewModel.deleteCommodityCompleted()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun observeUserPreferencesForEditCommodity() {
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            editCommodity(it.token)
        }
    }

    private fun editCommodity(token: String) {
        val idCommodity = args.commodity.id
        val blossomsTreeDate = binding.tfTreeBlossom.text.toString()
        val harvestingDate = binding.tfHarvestDate.text.toString()
        val fruitGrade = binding.tfFruitGrade.text.toString()
        val stock = binding.tfProductionResult.text.toString().toInt()

        val inputEditCommodity = InputEditCommodity(
            idCommodity, blossomsTreeDate, harvestingDate, fruitGrade, stock
        )

        viewModel.editCommodity(token, inputEditCommodity)
    }

    private fun observeEditCommodityUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.editCommodityUiState.collect { ui ->
                    when (ui) {
                        is ApiResult.Success -> {
                            binding.loadingIndicator.hide()
                            requireContext().showToast("Berhasil disimpan.")
                            mainFlowNavController?.popBackStack()
                            viewModel.editCommodityCompleted()
                        }
                        is ApiResult.Loading -> {
                            binding.loadingIndicator.show()
                        }
                        is ApiResult.Error -> {
                            binding.loadingIndicator.hide()
                            // TODO: code error handling here!
                            requireContext().showToast(
                                getString(
                                    R.string.something_went_wrong_with_message,
                                    ui.message
                                )
                            )
                            viewModel.editCommodityCompleted()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
package com.wibisa.fruitcollector.ui.transaction.farmertransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.hide
import com.wibisa.fruitcollector.core.util.isNotNullOrEmpty
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentCreateFarmerTransactionStepTwoBinding
import com.wibisa.fruitcollector.databinding.ItemTransFarmerCommodityBinding
import com.wibisa.fruitcollector.viewmodel.CreateFarmerTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateFarmerTransactionStepTwoFragment : Fragment() {

    private lateinit var binding: FragmentCreateFarmerTransactionStepTwoBinding
    private lateinit var partialBinding: ItemTransFarmerCommodityBinding
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val viewModel: CreateFarmerTransactionViewModel by hiltNavGraphViewModels(R.id.createTransactionWithFarmer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.totalPrice.value = 0
                mainFlowNavController?.popBackStack()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateFarmerTransactionStepTwoBinding.inflate(inflater, container, false)
        partialBinding = binding.partialFruitCommodity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()

        observeCreateFarmerTransactionUiState()
    }

    private fun componentUiSetup() {

        binding.btnBack.setOnClickListener {
            viewModel.totalPrice.value = 0
            mainFlowNavController?.popBackStack()
        }

        bindSelectedCommodity()

        observeTotalPrice()

        binding.btnNext.setOnClickListener {
            val isValid = binding.tfPrice.isNotNullOrEmpty(getString(R.string.required)) and
                    binding.tfQuantity.isNotNullOrEmpty(getString(R.string.required))

            if (isValid)
                calculateTotalPrice()
        }

        binding.btnSave.setOnClickListener {
            observeUserPreferencesForCreateFarmerTransaction()
        }
    }

    private fun calculateTotalPrice() {
        val quantity = binding.tfQuantity.text.toString().toInt()
        val price = binding.tfPrice.text.toString().toInt()

        viewModel.totalPrice.value = quantity * price
        binding.btnNext.hide()
    }

    private fun observeTotalPrice() {
        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.tvPriceTotal.text = it.toString()
        }
    }

    private fun bindSelectedCommodity() {
        viewModel.selectedCommodity.observe(viewLifecycleOwner) {
            partialBinding.apply {
                val fruitAndGrade = getString(
                    R.string.fruit_name_and_grade,
                    it.commodityName,
                    it.grade
                )
                tvFruitNameAndGrade.text = fruitAndGrade
                tvFarmerName.text = it.farmerName
                tvHarvestDate.text =
                    getString(R.string.harvest_date_with_date, it.harvestDate)
                tvStock.text = getString(R.string.stock_with_value, it.stock)
            }
        }
    }

    private fun observeUserPreferencesForCreateFarmerTransaction() {
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            val isValid =
                viewModel.selectedCommodity.value != null && viewModel.totalPrice.value != null

            if (isValid) {
                createFarmerTransaction(it.token)
            } else {
                requireContext().showToast(
                    getString(
                        R.string.something_went_wrong_with_message,
                        "Input belum benar."
                    )
                )
            }
        }
    }

    private fun createFarmerTransaction(token: String) {
        val quantity = binding.tfQuantity.text.toString().toInt()
        val price = binding.tfPrice.text.toString().toInt()

        viewModel.createFarmerTransaction(token, quantity, price)
    }

    private fun observeCreateFarmerTransactionUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.createFarmerTransactionUiState.collect { ui ->
                    when (ui) {
                        is ApiResult.Success -> {
                            binding.loadingIndicator.hide()
                            mainFlowNavController?.navigate(R.id.action_global_homeScreen)
                            requireContext().showToast(ui.data)
                            viewModel.createFarmerTransactionCompleted()
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
                            viewModel.createFarmerTransactionCompleted()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

}
package com.wibisa.fruitcollector.ui.transaction.farmertransaction

import android.os.Bundle
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
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.adapter.CommodityListener
import com.wibisa.fruitcollector.adapter.CreateTransFarmerCommodityAdapter
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentCreateFarmerTransactionStepOneBinding
import com.wibisa.fruitcollector.viewmodel.CreateFarmerTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateFarmerTransactionStepOneFragment : Fragment() {

    private lateinit var binding: FragmentCreateFarmerTransactionStepOneBinding
    private lateinit var adapter: CreateTransFarmerCommodityAdapter
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val viewModel: CreateFarmerTransactionViewModel by hiltNavGraphViewModels(R.id.createTransactionWithFarmer)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateFarmerTransactionStepOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()

        observeUserPreferencesForGetCommodities()

        observeCommoditiesUiState()
    }

    private fun componentUiSetup() {

        binding.btnBack.setOnClickListener { mainFlowNavController?.popBackStack() }

        fruitCommodityAdapterSetup()
    }

    private fun fruitCommodityAdapterSetup() {
        adapter = CreateTransFarmerCommodityAdapter(
            context = requireContext(),
            clickListener = CommodityListener {
                viewModel.selectedCommodity.value = it
                mainFlowNavController?.navigate(R.id.action_createFarmerTransactionStepOne_to_createFarmerTransactionStepTwo)
            })
        binding.rvFruitCommodity.adapter = adapter
    }

    private fun observeUserPreferencesForGetCommodities() {
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            viewModel.getCommodity(it.token)
        }
    }

    private fun observeCommoditiesUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.commoditiesUiState.collect { ui ->
                    when (ui) {
                        is ApiResult.Success -> {
                            binding.loadingIndicator.hide()
                            adapter.submitList(ui.data)
                            viewModel.getCommodityCompleted()
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
                            viewModel.getCommodityCompleted()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

}
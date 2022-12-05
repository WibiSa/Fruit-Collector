package com.wibisa.fruitcollector.ui.transaction.customertransaction

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
import com.wibisa.fruitcollector.adapter.CreateTransCustomerCommodityAdapter
import com.wibisa.fruitcollector.adapter.CreateTransCustomerCommodityListener
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentCreateCustomerTransactionStepOneBinding
import com.wibisa.fruitcollector.viewmodel.CreateCustomerTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateCustomerTransactionStepOneFragment : Fragment() {

    private lateinit var binding: FragmentCreateCustomerTransactionStepOneBinding
    private lateinit var adapter: CreateTransCustomerCommodityAdapter
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val viewModel: CreateCustomerTransactionViewModel by hiltNavGraphViewModels(R.id.createTransactionWithCustomer)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            FragmentCreateCustomerTransactionStepOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()

        observeListFarmerTransaction()
    }

    private fun componentUiSetup() {

        binding.appbar.setNavigationOnClickListener { mainFlowNavController?.popBackStack() }

        transCustomerCommodityAdapterSetup()
    }

    private fun transCustomerCommodityAdapterSetup() {
        adapter = CreateTransCustomerCommodityAdapter(
            context = requireContext(),
            clickListener = CreateTransCustomerCommodityListener {
                viewModel.selectedFarmerTransaction.value = it
                mainFlowNavController?.navigate(R.id.action_createCustomerTransactionStepOne_to_createCustomerTransactionStepTwo)
            })
        binding.rvAvailableCommodity.adapter = adapter
    }

    private fun observeListFarmerTransaction() {
        viewModel.userPreferences.observe(viewLifecycleOwner) { userPref ->
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getListFarmerTransaction(userPref.token).collect { ui ->
                        when (ui) {
                            is ApiResult.Success -> {
                                binding.loadingIndicator.hide()
                                adapter.submitList(ui.data)
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
    }
}
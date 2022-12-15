package com.wibisa.fruitcollector.ui.transaction.customertransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.adapter.CustomerTransactionAdapter
import com.wibisa.fruitcollector.adapter.CustomerTransactionListener
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentCustomerTransactionBinding
import com.wibisa.fruitcollector.viewmodel.CustomerTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CustomerTransactionFragment : Fragment() {

    private lateinit var binding: FragmentCustomerTransactionBinding
    private lateinit var adapter: CustomerTransactionAdapter
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val viewModel: CustomerTransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCustomerTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()

        observeListCustomerTransaction()
    }

    private fun componentUiSetup() {
        binding.appbar.setNavigationOnClickListener {
            mainFlowNavController?.popBackStack()
        }

        customerTransactionAdapterSetup()
    }

    private fun customerTransactionAdapterSetup() {
        adapter = CustomerTransactionAdapter(
            context = requireContext(),
            clickListener = CustomerTransactionListener {
                val destination =
                    CustomerTransactionFragmentDirections.actionCustomerTransactionToCustomerTransactionDetails(
                        it
                    )
                mainFlowNavController?.navigate(destination)
            })
        binding.rvCustomerTransaction.adapter = adapter
    }

    private fun observeListCustomerTransaction() {
        viewModel.userPreferences.observe(viewLifecycleOwner) { userPref ->
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getListCustomerTransaction(userPref.token).collect { ui ->
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
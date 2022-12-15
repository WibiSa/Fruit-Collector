package com.wibisa.fruitcollector.ui.transaction.farmertransaction

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
import com.wibisa.fruitcollector.adapter.FarmerTransactionAdapter
import com.wibisa.fruitcollector.adapter.FarmerTransactionListener
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentFarmerTransactionBinding
import com.wibisa.fruitcollector.viewmodel.FarmerTransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FarmerTransactionFragment : Fragment() {

    private lateinit var binding: FragmentFarmerTransactionBinding
    private lateinit var adapter: FarmerTransactionAdapter
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val viewModel: FarmerTransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFarmerTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()

        observeListFarmerTransaction()
    }

    private fun componentUiSetup() {
        binding.appbar.setNavigationOnClickListener {
            mainFlowNavController?.popBackStack()
        }

        farmerTransactionAdapterSetup()
    }

    private fun farmerTransactionAdapterSetup() {
        adapter = FarmerTransactionAdapter(
            context = requireContext(),
            clickListener = FarmerTransactionListener {
                val destination =
                    FarmerTransactionFragmentDirections.actionFarmerTransactionToFarmerTransactionDetails(it)
                mainFlowNavController?.navigate(destination)
            })
        binding.rvFarmerTransaction.adapter = adapter
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
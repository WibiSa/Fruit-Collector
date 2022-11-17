package com.wibisa.fruitcollector.ui.commodity

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
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.adapter.CommodityAdapter
import com.wibisa.fruitcollector.adapter.CommodityListener
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentCommodityBinding
import com.wibisa.fruitcollector.viewmodel.CommodityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommodityFragment : Fragment() {

    private lateinit var binding: FragmentCommodityBinding
    private lateinit var adapter: CommodityAdapter
    private val viewModel: CommodityViewModel by viewModels()
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCommodityBinding.inflate(inflater, container, false)
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
        adapter = CommodityAdapter(clickListener = CommodityListener {
            val destination = CommodityFragmentDirections.actionCommodityToCommodityDetails(it)
            mainFlowNavController?.navigate(destination)
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
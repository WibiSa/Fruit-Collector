package com.wibisa.fruitcollector.ui.commodity

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
import com.wibisa.fruitcollector.adapter.FruitsAdapter
import com.wibisa.fruitcollector.adapter.FruitsListener
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentRecordCommodityStepTwoBinding
import com.wibisa.fruitcollector.viewmodel.RecordCommodityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecordCommodityStepTwoFragment : Fragment() {

    private lateinit var binding: FragmentRecordCommodityStepTwoBinding
    private lateinit var adapter: FruitsAdapter
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val viewModel: RecordCommodityViewModel by hiltNavGraphViewModels(R.id.recordCommodityGraph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecordCommodityStepTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()

        observeUserPreferencesForGetFruits()

        observeFruitsUiState()
    }

    private fun componentUiSetup() {

        binding.btnBack.setOnClickListener { mainFlowNavController?.popBackStack() }

        binding.btnSave.setOnClickListener {
            // TODO: business logic!
            CoroutineScope(Dispatchers.Main).launch {
                binding.loadingIndicator.show()
                delay(1500)
                binding.loadingIndicator.hide()
                requireContext().showToast("Berhasil disimpan")
                mainFlowNavController?.navigate(R.id.action_global_homeScreen)
            }
        }

        fruitsAdapterSetup()
    }

    private fun fruitsAdapterSetup() {
        adapter = FruitsAdapter(clickListener = FruitsListener {
            // TODO: pass data with viewModel navGraph scope
            viewModel.fruitId.value = it.id
        })
        binding.rvFruits.adapter = adapter
    }

    private fun observeUserPreferencesForGetFruits() {
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            viewModel.getFruits(it.token)
        }
    }

    private fun observeFruitsUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fruitsUiState.collect { ui ->
                    when (ui) {
                        is ApiResult.Success -> {
                            binding.loadingIndicator.hide()
                            adapter.submitList(ui.data)
                            viewModel.getFruitsCompleted()
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
                            viewModel.getFruitsCompleted()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
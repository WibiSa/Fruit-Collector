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
import com.wibisa.fruitcollector.adapter.FarmersAdapter
import com.wibisa.fruitcollector.adapter.FarmersListener
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentRecordCommodityStepOneBinding
import com.wibisa.fruitcollector.viewmodel.RecordCommodityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecordCommodityStepOneFragment : Fragment() {

    private lateinit var binding: FragmentRecordCommodityStepOneBinding
    private lateinit var adapter: FarmersAdapter
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val viewModel: RecordCommodityViewModel by hiltNavGraphViewModels(R.id.recordCommodityGraph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecordCommodityStepOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()

        observeUserPreferencesForGetFarmers()

        observeFarmersUiState()
    }

    private fun componentUiSetup() {

        binding.btnBack.setOnClickListener {
            mainFlowNavController?.popBackStack()
            // TODO: clear data from viewModel
        }

        farmersAdapterSetup()
    }

    private fun farmersAdapterSetup() {
        adapter = FarmersAdapter(clickListener = FarmersListener {
            // TODO: save id farmer to viewModel
            viewModel.farmerId.value = it.id
            // TODO: navigate to step 2
            mainFlowNavController?.navigate(R.id.action_recordCommodityStepOne_to_recordCommodityStepTwo)
        })
        binding.rvFarmers.adapter = adapter
    }

    private fun observeUserPreferencesForGetFarmers() {
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            viewModel.getFarmers(it.token)
        }
    }

    private fun observeFarmersUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.farmersUiState.collect { ui ->
                    when (ui) {
                        is ApiResult.Success -> {
                            binding.loadingIndicator.hide()
                            adapter.submitList(ui.data)
                            viewModel.getFarmersCompleted()
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
                            viewModel.getFarmersCompleted()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
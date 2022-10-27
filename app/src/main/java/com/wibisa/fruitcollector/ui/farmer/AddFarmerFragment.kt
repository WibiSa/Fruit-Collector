package com.wibisa.fruitcollector.ui.farmer

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
import com.wibisa.fruitcollector.core.domain.model.InputAddFarmer
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.hideKeyboard
import com.wibisa.fruitcollector.core.util.isNotNullOrEmpty
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentAddFarmerBinding
import com.wibisa.fruitcollector.viewmodel.AddFarmerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFarmerFragment : Fragment() {

    private lateinit var binding: FragmentAddFarmerBinding
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val viewModel: AddFarmerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddFarmerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()

        observeAddFarmerUiState()
    }

    private fun componentUiSetup() {

        binding.btnBack.setOnClickListener { mainFlowNavController?.popBackStack() }

        binding.btnSave.setOnClickListener {
            val isValid = binding.tfName.isNotNullOrEmpty(getString(R.string.required)) and
                    binding.tfLandArea.isNotNullOrEmpty(getString(R.string.required)) and
                    binding.tfLandLocation.isNotNullOrEmpty(getString(R.string.required)) and
                    binding.tfProductionEstimate.isNotNullOrEmpty(getString(R.string.required)) and
                    binding.tfNumberOfTree.isNotNullOrEmpty(getString(R.string.required))

            hideKeyboard()

            if (isValid)
                observeUserPreferencesForAddFarmer()
        }
    }

    private fun observeUserPreferencesForAddFarmer() {
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            addFarmer(it.token)
        }
    }

    private fun addFarmer(token: String) {
        val name = binding.tfName.text.toString()
        val landLocation = binding.tfLandLocation.text.toString()
        val numberOfTree = binding.tfNumberOfTree.text.toString().trim().toInt()
        val estimateProduction = binding.tfProductionEstimate.text.toString().trim().toInt()
        val landSize = binding.tfLandArea.text.toString().trim().toFloat()

        val inputAddFarmer = InputAddFarmer(
            name = name,
            landLocation = landLocation,
            numberOfTree = numberOfTree,
            estimationProduction = estimateProduction,
            landSize = landSize
        )

        viewModel.addFarmer(token, inputAddFarmer)
    }

    private fun observeAddFarmerUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addFarmerUiState.collect { ui ->
                    when (ui) {
                        is ApiResult.Success -> {
                            binding.loadingIndicator.hide()
                            mainFlowNavController?.popBackStack(R.id.homeScreen, false)
                            requireContext().showToast(ui.data)
                            viewModel.addFarmerCompleted()
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
                            viewModel.addFarmerCompleted()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
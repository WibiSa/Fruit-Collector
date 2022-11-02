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
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.domain.model.Farmer
import com.wibisa.fruitcollector.core.domain.model.InputAddFarmer
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.hideKeyboard
import com.wibisa.fruitcollector.core.util.isNotNullOrEmpty
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentFarmerDetailsBinding
import com.wibisa.fruitcollector.viewmodel.FarmerDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FarmerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFarmerDetailsBinding
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val viewModel: FarmerDetailsViewModel by viewModels()
    private val args: FarmerDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFarmerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()

        observeAddFarmerUiState()

        observeDeleteFarmerUiState()
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
                observeUserPreferencesForEditFarmer()
        }

        binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Hapus petani")
                .setMessage("Anda yakin ingin menghapus petani?")
                .setPositiveButton("Ya") { dialog, _ ->
                    observeUserPreferencesForDeleteFarmer()
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        // set initial farmer profile
        bindFarmer(args.farmer)
    }

    private fun observeUserPreferencesForEditFarmer() {
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            editFarmer(it.token)
        }
    }

    private fun editFarmer(token: String) {
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

        viewModel.editFarmer(token, args.farmer.id, inputAddFarmer)
    }

    private fun observeAddFarmerUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.editFarmerUiState.collect { ui ->
                    when (ui) {
                        is ApiResult.Success -> {
                            binding.loadingIndicator.hide()
                            bindFarmer(ui.data)
                            requireContext().showToast("Berhasil disimpan.")
                            viewModel.editFarmerCompleted()
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
                            viewModel.editFarmerCompleted()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun bindFarmer(farmer: Farmer) {
        binding.apply {
            tfName.setText(farmer.name)
            tfLandArea.setText(farmer.landSize.toString())
            tfNumberOfTree.setText(farmer.numberTree.toString())
            tfProductionEstimate.setText(farmer.estimationProduction.toString())
            tfLandLocation.setText(farmer.landLocation)
        }
    }

    private fun observeUserPreferencesForDeleteFarmer() {
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            viewModel.deleteFarmer(it.token, args.farmer.id)
        }
    }

    private fun observeDeleteFarmerUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.deleteFarmerUiState.collect { ui ->
                    when (ui) {
                        is ApiResult.Success -> {
                            binding.loadingIndicator.hide()
                            mainFlowNavController?.popBackStack()
                            requireContext().showToast(ui.data)
                            viewModel.deleteFarmerCompleted()
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
                            viewModel.deleteFarmerCompleted()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
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
import com.wibisa.fruitcollector.adapter.FarmersAdapter
import com.wibisa.fruitcollector.adapter.FarmersListener
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentFarmersBinding
import com.wibisa.fruitcollector.viewmodel.FarmersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FarmersFragment : Fragment() {

    private lateinit var binding: FragmentFarmersBinding
    private lateinit var adapter: FarmersAdapter
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val viewModel: FarmersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFarmersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()

        observeUserPreferencesForGetFarmers()

        observeFarmersUiState()
    }

    private fun componentUiSetup() {

        binding.btnBack.setOnClickListener { mainFlowNavController?.popBackStack() }

        farmersAdapterSetup()
    }

    private fun farmersAdapterSetup() {
        adapter = FarmersAdapter(clickListener = FarmersListener {
            val destination = FarmersFragmentDirections.actionFarmersToFarmerDetails(it)
            mainFlowNavController?.navigate(destination)
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

    // TODO: jika list farmer kosong, belum ada state view!
}
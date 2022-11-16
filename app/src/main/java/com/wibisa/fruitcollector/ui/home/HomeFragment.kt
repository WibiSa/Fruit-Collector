package com.wibisa.fruitcollector.ui.home

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.adapter.HomeMenuAdapter
import com.wibisa.fruitcollector.adapter.HomeMenuListener
import com.wibisa.fruitcollector.adapter.ImageSliderAdapter
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.LocalResourceData
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentHomeBinding
import com.wibisa.fruitcollector.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var localResourceData: LocalResourceData
    private lateinit var menuAdapter: HomeMenuAdapter
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val baseNavController: NavController? by lazy { activity?.findNavController(R.id.base_nav_host) }
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()

        observeUserPreferences()

        observeLogoutUiState()
    }

    private fun componentUiSetup() {
        localResourceData = LocalResourceData(requireContext())

        menuAdapterSetup()

        imageSliderSetup()

        binding.tvWelcomeUser.setOnClickListener {
            makeLogoutDialogAlert()
        }
    }

    private fun observeUserPreferences() {
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            binding.tvWelcomeUser.text = getString(R.string.welcome_user, it.name)
        }
    }

    private fun observeUserPreferencesForLogout() {
        viewModel.userPreferences.observe(viewLifecycleOwner) {
            viewModel.logout(it.token)
        }
    }

    private fun observeLogoutUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.logoutUiState.collect { logoutUi ->
                    when (logoutUi) {
                        is ApiResult.Success -> {
                            binding.loadingIndicator.hide()
                            viewModel.clearUserPreferences()
                            baseNavController?.navigate(R.id.action_baseMainFlow_to_baseAuthentication)
                            viewModel.logoutCompleted()
                            val message = logoutUi.data.message
                            requireContext().showToast(message)
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
                                    logoutUi.message
                                )
                            )
                            viewModel.logoutCompleted()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun menuAdapterSetup() {
        val menu = localResourceData.homeMenu

        menuAdapter = HomeMenuAdapter(HomeMenuListener {
            menuNavRoute(it.name)
        })
        binding.rvHomeMenu.adapter = menuAdapter
        menuAdapter.submitList(menu)

    }

    private fun menuNavRoute(menuName: String) {
        when (menuName) {
            "Tambah Petani" -> {
                mainFlowNavController?.navigate(R.id.action_homeScreen_to_addFarmer)
            }
            "Petani" -> {
                mainFlowNavController?.navigate(R.id.action_homeScreen_to_farmers)
            }
            "Catat Komoditas" -> {
                mainFlowNavController?.navigate(R.id.action_homeScreen_to_recordCommodityGraph)
            }
            "Komoditas" -> {
                mainFlowNavController?.navigate(R.id.action_homeScreen_to_commodity)
            }
            "Buat Transaksi" -> {
                mainFlowNavController?.navigate(R.id.action_homeScreen_to_transactionLanding)
            }
            else -> {}
        }
    }

    private fun imageSliderSetup() {
        val banners = localResourceData.imageBanners

        imageSliderAdapter = ImageSliderAdapter(banners)
        binding.imgSlider.setSliderAdapter(imageSliderAdapter)
        binding.imgSlider.isAutoCycle = true
        binding.imgSlider.startAutoCycle()
    }

    private fun makeLogoutDialogAlert() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Perhatian")
            .setMessage("Apakah anda ingin keluar?")
            .setNegativeButton("Batal") { dialog, _ -> dialog.cancel() }
            .setPositiveButton("Keluar") { dialog, _ ->
                observeUserPreferencesForLogout()
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}
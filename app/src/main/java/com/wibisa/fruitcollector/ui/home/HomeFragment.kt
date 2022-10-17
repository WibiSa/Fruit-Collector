package com.wibisa.fruitcollector.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.adapter.HomeMenuAdapter
import com.wibisa.fruitcollector.adapter.HomeMenuListener
import com.wibisa.fruitcollector.adapter.ImageSliderAdapter
import com.wibisa.fruitcollector.core.util.LocalResourceData
import com.wibisa.fruitcollector.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var localResourceData: LocalResourceData
    private lateinit var menuAdapter: HomeMenuAdapter
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }

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
    }

    private fun componentUiSetup() {
        localResourceData = LocalResourceData(requireContext())

        menuAdapterSetup()

        imageSliderSetup()
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
}
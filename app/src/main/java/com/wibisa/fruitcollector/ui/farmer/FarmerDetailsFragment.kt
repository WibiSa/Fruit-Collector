package com.wibisa.fruitcollector.ui.farmer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.wibisa.fruitcollector.core.domain.model.Farmer
import com.wibisa.fruitcollector.core.util.hideKeyboard
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentFarmerDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FarmerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFarmerDetailsBinding
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
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
    }

    private fun componentUiSetup() {

        binding.btnBack.setOnClickListener { mainFlowNavController?.popBackStack() }

        binding.btnSave.setOnClickListener {
            // TODO: validation input
            val isValid = true
            hideKeyboard()

            if (isValid)
                CoroutineScope(Dispatchers.Main).launch {
                    binding.loadingIndicator.show()
                    delay(1500)
                    binding.loadingIndicator.hide()
                    save()
                }
        }

        binding.btnDelete.setOnClickListener { deleteFarmer() }

        // set initial farmer profile
        bindFarmer(args.farmer)
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

    private fun deleteFarmer() {
        requireContext().showToast("Fitur Dalam Pengembangan.")
    }

    private fun save() {
        requireContext().showToast("Berhasil disimpan.")
    }
}
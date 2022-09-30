package com.wibisa.fruitcollector.ui.farmer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.core.util.hideKeyboard
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentAddFarmerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddFarmerFragment : Fragment() {

    private lateinit var binding: FragmentAddFarmerBinding
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }

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
    }

    private fun save() {
        // TODO: save data farmer to server
        requireContext().showToast("Petani Berhasil ditambahkan.")
        mainFlowNavController?.popBackStack()
    }
}
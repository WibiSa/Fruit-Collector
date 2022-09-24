package com.wibisa.fruitcollector.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.util.hideKeyboard
import com.wibisa.fruitcollector.core.util.isNotNullOrEmpty
import com.wibisa.fruitcollector.core.util.showOrHideHint
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()
    }

    private fun componentUiSetup() {

        binding.apply {
            tfName.showOrHideHint()
            tfEmail.showOrHideHint()
            tfPhone.showOrHideHint()
            tfPassword.showOrHideHint()

            btnBack.setOnClickListener {
                authNavController?.popBackStack()
            }

            btnRegister.setOnClickListener {
                val isValid = tfName.isNotNullOrEmpty(getString(R.string.required)) and
                        tfEmail.isNotNullOrEmpty(getString(R.string.required)) and
                        tfPassword.isNotNullOrEmpty(getString(R.string.required)) and
                        tfPhone.isNotNullOrEmpty(getString(R.string.required))

                hideKeyboard()

                if (isValid)
                    CoroutineScope(Dispatchers.Main).launch {
                        loadingIndicator.show()
                        delay(2000)
                        loadingIndicator.hide()
                        register()
                    }
            }
        }
    }

    private fun register() {
        val name = binding.tfName.text.toString().trim()
        val email = binding.tfEmail.text.toString().trim()
        val phone = binding.tfPhone.text.toString().trim()
        val password = binding.tfPassword.text.toString().trim()

        requireContext().showToast("Pembuatan Akun $name berhasil.")
        authNavController?.popBackStack()
    }
}
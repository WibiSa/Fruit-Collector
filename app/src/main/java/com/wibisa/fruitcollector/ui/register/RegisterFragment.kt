package com.wibisa.fruitcollector.ui.register

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
import com.wibisa.fruitcollector.core.domain.model.InputRegister
import com.wibisa.fruitcollector.core.domain.model.UserPreferences
import com.wibisa.fruitcollector.core.util.*
import com.wibisa.fruitcollector.databinding.FragmentRegisterBinding
import com.wibisa.fruitcollector.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private val authNavController: NavController? by lazy { view?.findNavController() }
    private val baseNavController: NavController? by lazy { activity?.findNavController(R.id.base_nav_host) }

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

        observeRegisterUiState()
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
                    register()
            }
        }
    }

    private fun register() {
        val name = binding.tfName.text.toString().trim()
        val email = binding.tfEmail.text.toString().trim()
        val phone = binding.tfPhone.text.toString().trim()
        val password = binding.tfPassword.text.toString().trim()

        val inputRegister = InputRegister(
            name = name,
            email = email,
            phone = phone,
            password = password,
            passwordConfirm = password
        )

        viewModel.register(inputRegister)
    }

    private fun observeRegisterUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerUiState.collect { registerUi ->
                    when (registerUi) {
                        is ApiResult.Success -> {
                            binding.loadingIndicator.hide()
                            val userPreferences = UserPreferences(
                                name = registerUi.data.name,
                                userId = registerUi.data.id,
                                token = registerUi.data.token
                            )
                            viewModel.saveUserPreferences(userPreferences)
                            delay(1000)
                            baseNavController?.navigate(R.id.action_baseAuthentication_to_baseMainFlow)
                            viewModel.registerCompleted()
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
                                    registerUi.message
                                )
                            )
                            viewModel.registerCompleted()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
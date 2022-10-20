package com.wibisa.fruitcollector.ui.login

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.domain.model.InputLogin
import com.wibisa.fruitcollector.core.domain.model.UserPreferences
import com.wibisa.fruitcollector.core.util.*
import com.wibisa.fruitcollector.databinding.FragmentLoginBinding
import com.wibisa.fruitcollector.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val authNavController: NavController? by lazy { view?.findNavController() }
    private val baseNavController: NavController? by lazy { activity?.findNavController(R.id.base_nav_host) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()

        observeLoginUiState()
    }

    private fun componentUiSetup() {

        val spannableString = SpannableString(getString(R.string.register_here))
        val textColor = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.green))
        spannableString.setSpan(textColor, 18, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvGoToRegister.text = spannableString

        binding.tfEmail.showOrHideHint()
        binding.tfPassword.showOrHideHint()

        binding.tvGoToRegister.setOnClickListener { authNavController?.navigate(R.id.action_login_to_register) }

        binding.btnLogin.setOnClickListener {
            val isValid = binding.tfEmail.isNotNullOrEmpty(getString(R.string.required)) and
                    binding.tfPassword.isNotNullOrEmpty(getString(R.string.required))

            hideKeyboard()

            if (isValid)
                login()
        }
    }

    private fun login() {
        val email = binding.tfEmail.text.toString().trim()
        val password = binding.tfPassword.text.toString().trim()

        val inputLogin = InputLogin(email, password)
        viewModel.login(inputLogin)
    }

    private fun observeLoginUiState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginUiState.collect { loginUi ->
                    when (loginUi) {
                        is ApiResult.Success -> {
                            binding.loadingIndicator.hide()
                            val userPreferences = UserPreferences(
                                name = loginUi.data.name,
                                userId = loginUi.data.id,
                                token = loginUi.data.token
                            )
                            viewModel.saveUserPreferences(userPreferences)
                            delay(1000)
                            baseNavController?.navigate(R.id.action_baseAuthentication_to_baseMainFlow)
                            viewModel.loginCompleted()
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
                                    loginUi.message
                                )
                            )
                            viewModel.loginCompleted()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
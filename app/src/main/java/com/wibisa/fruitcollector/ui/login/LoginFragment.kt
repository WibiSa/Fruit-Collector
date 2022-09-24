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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.util.hideKeyboard
import com.wibisa.fruitcollector.core.util.isNotNullOrEmpty
import com.wibisa.fruitcollector.core.util.showOrHideHint
import com.wibisa.fruitcollector.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
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
                CoroutineScope(Dispatchers.Main).launch {
                    binding.loadingIndicator.show()
                    delay(1500)
                    binding.loadingIndicator.hide()
                    login()
                }
        }
    }

    private fun login() {
//        val email = binding.tfEmail.text.toString().trim()
//        val password = binding.tfPassword.text.toString().trim()
//        val message = "$email, $password"

        baseNavController?.navigate(R.id.action_baseAuthentication_to_baseMainFlow)
    }
}
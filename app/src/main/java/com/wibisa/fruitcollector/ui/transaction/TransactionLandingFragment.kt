package com.wibisa.fruitcollector.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.databinding.FragmentTransactionLandingBinding

class TransactionLandingFragment : Fragment() {

    private lateinit var binding: FragmentTransactionLandingBinding
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTransactionLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener { mainFlowNavController?.popBackStack() }

        binding.cardTransactionWithFarmer.setOnClickListener {
            mainFlowNavController?.navigate(R.id.action_transactionLanding_to_createTransactionWithFarmer)
        }

        binding.cardTransactionWithCustomer.setOnClickListener {
            // TODO:
        }
    }
}
package com.wibisa.fruitcollector.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.util.hide
import com.wibisa.fruitcollector.core.util.show
import com.wibisa.fruitcollector.databinding.FragmentBaseMainFlowBinding

class BaseMainFlowFragment : Fragment() {

    private lateinit var binding: FragmentBaseMainFlowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBaseMainFlowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainFlowNavController = requireActivity().findNavController(R.id.main_flow_nav_host)
        binding.bottomNavBar.setupWithNavController(mainFlowNavController)

        mainFlowNavController.addOnDestinationChangedListener { _, destination, _ ->
            val isValid = destination.id == R.id.homeScreen || destination.id == R.id.account

            if (isValid)
                binding.bottomNavBar.show()
            else
                binding.bottomNavBar.hide()
        }

    }
}
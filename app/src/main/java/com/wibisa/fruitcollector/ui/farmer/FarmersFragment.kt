package com.wibisa.fruitcollector.ui.farmer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.adapter.FarmersAdapter
import com.wibisa.fruitcollector.adapter.FarmersListener
import com.wibisa.fruitcollector.core.util.LocalResourceData
import com.wibisa.fruitcollector.databinding.FragmentFarmersBinding

class FarmersFragment : Fragment() {

    private lateinit var binding: FragmentFarmersBinding
    private lateinit var adapter: FarmersAdapter
    private lateinit var localResourceData: LocalResourceData
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFarmersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()
    }

    private fun componentUiSetup() {
        localResourceData = LocalResourceData(requireContext())

        binding.appBar.setNavigationOnClickListener { mainFlowNavController?.popBackStack() }

        farmersAdapterSetup()
    }

    private fun farmersAdapterSetup() {
        val farmers = localResourceData.dummyFarmers

        adapter = FarmersAdapter(FarmersListener {
            // TODO: pass data to detail
            mainFlowNavController?.navigate(R.id.action_farmers_to_farmerDetails)
        })
        binding.rvFarmers.adapter = adapter
        adapter.submitList(farmers)
    }
}
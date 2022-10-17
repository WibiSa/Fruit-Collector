package com.wibisa.fruitcollector.ui.transaction.framertransaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.adapter.CommodityAdapter
import com.wibisa.fruitcollector.adapter.CommodityListener
import com.wibisa.fruitcollector.core.util.LocalResourceData
import com.wibisa.fruitcollector.databinding.FragmentCreateFarmerTransactionStepOneBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CreateFarmerTransactionStepOneFragment : Fragment() {

    private lateinit var binding: FragmentCreateFarmerTransactionStepOneBinding
    private lateinit var adapter: CommodityAdapter
    private lateinit var localResourceData: LocalResourceData
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateFarmerTransactionStepOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()
    }

    private fun componentUiSetup() {

        localResourceData = LocalResourceData(requireContext())

        binding.btnBack.setOnClickListener { mainFlowNavController?.popBackStack() }

        // TODO: adapter rv! and interaction
        fruitCommodityAdapterSetup()
    }

    private fun fruitCommodityAdapterSetup() {
        adapter = CommodityAdapter(clickListener = CommodityListener {
            // TODO: pass data to step two!
            mainFlowNavController?.navigate(R.id.action_createFarmerTransactionStepOne_to_createFarmerTransactionStepTwo)
        })
        binding.rvFruitCommodity.adapter = adapter
        val commodities = localResourceData.dummyCommodities
        CoroutineScope(Dispatchers.Main).launch {
            binding.loadingIndicator.show()
            delay(1500)
            adapter.submitList(commodities)
            binding.loadingIndicator.hide()
        }
    }

}
package com.wibisa.fruitcollector.ui.transaction.framertransaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.util.LocalResourceData
import com.wibisa.fruitcollector.core.util.isNotNullOrEmpty
import com.wibisa.fruitcollector.databinding.FragmentCreateFarmerTransactionStepTwoBinding
import com.wibisa.fruitcollector.databinding.ItemFruitCommodityBinding

class CreateFarmerTransactionStepTwoFragment : Fragment() {

    private lateinit var binding: FragmentCreateFarmerTransactionStepTwoBinding
    private lateinit var partialBinding: ItemFruitCommodityBinding
    private lateinit var localResourceData: LocalResourceData
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateFarmerTransactionStepTwoBinding.inflate(inflater, container, false)
        partialBinding = binding.partialFruitCommodity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()
    }

    private fun componentUiSetup() {

        localResourceData = LocalResourceData(requireContext())

        binding.btnBack.setOnClickListener { mainFlowNavController?.popBackStack() }

        val dummyFruitComm = localResourceData.dummyCommodities[0]

        partialBinding.apply {
            tvFruitName.text = dummyFruitComm.commodityName
            tvFarmerName.text = dummyFruitComm.farmerName
            tvDate.text = dummyFruitComm.recordDate
        }

        binding.tvPriceTotal.text = "Rp 125.000"

        binding.btnSave.setOnClickListener {
            val isValid = binding.tfPrice.isNotNullOrEmpty(getString(R.string.required)) and
                    binding.tfQuantity.isNotNullOrEmpty(getString(R.string.required))

            if (isValid)
                mainFlowNavController?.popBackStack(R.id.homeScreen, false)
        }
    }

}
package com.wibisa.fruitcollector.ui.transaction.farmertransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.adapter.CommodityListener
import com.wibisa.fruitcollector.adapter.TransFarmerCommodityAdapter
import com.wibisa.fruitcollector.core.util.LocalResourceData
import com.wibisa.fruitcollector.databinding.FragmentCreateFarmerTransactionStepOneBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CreateFarmerTransactionStepOneFragment : Fragment() {

    private lateinit var binding: FragmentCreateFarmerTransactionStepOneBinding
    private lateinit var adapter: TransFarmerCommodityAdapter
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

        fruitCommodityAdapterSetup()
    }

    private fun fruitCommodityAdapterSetup() {
        adapter = TransFarmerCommodityAdapter(
            context = requireContext(),
            clickListener = CommodityListener {
                // TODO: pass data to step two!
                mainFlowNavController?.navigate(R.id.action_createFarmerTransactionStepOne_to_createFarmerTransactionStepTwo)
            })
        binding.rvFruitCommodity.adapter = adapter
        val commodities = localResourceData.dummyCommoditiesForTransFarmer
        CoroutineScope(Dispatchers.Main).launch {
            binding.loadingIndicator.show()
            delay(1500)
            adapter.submitList(commodities)
            binding.loadingIndicator.hide()
        }
    }

}
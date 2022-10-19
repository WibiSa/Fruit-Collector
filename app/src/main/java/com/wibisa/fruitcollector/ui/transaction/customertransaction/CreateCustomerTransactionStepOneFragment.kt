package com.wibisa.fruitcollector.ui.transaction.customertransaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.adapter.TransCustomerCommodityAdapter
import com.wibisa.fruitcollector.adapter.TransCustomerCommodityListener
import com.wibisa.fruitcollector.core.util.LocalResourceData
import com.wibisa.fruitcollector.databinding.FragmentCreateCustomerTransactionStepOneBinding
import com.wibisa.fruitcollector.databinding.FragmentCreateFarmerTransactionStepOneBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CreateCustomerTransactionStepOneFragment : Fragment() {

    private lateinit var binding: FragmentCreateCustomerTransactionStepOneBinding
    private lateinit var adapter: TransCustomerCommodityAdapter
    private lateinit var localResourceData: LocalResourceData
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            FragmentCreateCustomerTransactionStepOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()
    }

    private fun componentUiSetup() {

        localResourceData = LocalResourceData(requireContext())

        binding.appbar.setNavigationOnClickListener { mainFlowNavController?.popBackStack() }

        transCustomerCommodityAdapterSetup()
    }

    private fun transCustomerCommodityAdapterSetup() {
        adapter = TransCustomerCommodityAdapter(
            context = requireContext(),
            clickListener = TransCustomerCommodityListener {
                // TODO: pass data to step two!
                mainFlowNavController?.navigate(R.id.action_createCustomerTransactionStepOne_to_createCustomerTransactionStepTwo)
            })
        binding.rvAvailableCommodity.adapter = adapter
        val commodities = localResourceData.dummyTransFarmer
        CoroutineScope(Dispatchers.Main).launch {
            binding.loadingIndicator.show()
            delay(1500)
            adapter.submitList(commodities)
            binding.loadingIndicator.hide()
        }
    }
}
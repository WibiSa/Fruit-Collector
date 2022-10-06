package com.wibisa.fruitcollector.ui.commodity

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
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentCommodityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CommodityFragment : Fragment() {

    private lateinit var binding: FragmentCommodityBinding
    private lateinit var adapter: CommodityAdapter
    private lateinit var localResourceData: LocalResourceData
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCommodityBinding.inflate(inflater, container, false)
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
        adapter = CommodityAdapter(clickListener = CommodityListener {
            // TODO: pass data to detail!
            mainFlowNavController?.navigate(R.id.action_commodity_to_commodityDetails)
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
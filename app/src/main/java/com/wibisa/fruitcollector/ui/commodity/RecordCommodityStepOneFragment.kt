package com.wibisa.fruitcollector.ui.commodity

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
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentRecordCommodityStepOneBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecordCommodityStepOneFragment : Fragment() {

    private lateinit var binding: FragmentRecordCommodityStepOneBinding
    private lateinit var adapter: FarmersAdapter
    private lateinit var localResourceData: LocalResourceData
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecordCommodityStepOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()
    }

    private fun componentUiSetup() {
        localResourceData = LocalResourceData(requireContext())

        binding.btnBack.setOnClickListener { mainFlowNavController?.popBackStack() }

        farmersAdapterSetup()
    }

    private fun farmersAdapterSetup() {
        adapter = FarmersAdapter(isSuffixImageShow = false, clickListener = FarmersListener {
            // TODO: pass data with viewModel navGraph scope
            mainFlowNavController?.navigate(R.id.action_recordCommodityStepOne_to_recordCommodityStepTwo)
        })
        binding.rvFarmers.adapter = adapter
        val farmers = localResourceData.dummyFarmers
        CoroutineScope(Dispatchers.Main).launch {
            binding.loadingIndicator.show()
            delay(1500)
            adapter.submitList(farmers)
            binding.loadingIndicator.hide()
        }
    }
}
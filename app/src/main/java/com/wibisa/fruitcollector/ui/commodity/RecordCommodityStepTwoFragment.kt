package com.wibisa.fruitcollector.ui.commodity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.adapter.FruitsAdapter
import com.wibisa.fruitcollector.adapter.FruitsListener
import com.wibisa.fruitcollector.core.util.LocalResourceData
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentRecordCommodityStepTwoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecordCommodityStepTwoFragment : Fragment() {

    private lateinit var binding: FragmentRecordCommodityStepTwoBinding
    private lateinit var adapter: FruitsAdapter
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private lateinit var localResourceData: LocalResourceData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecordCommodityStepTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()
    }

    private fun componentUiSetup() {
        localResourceData = LocalResourceData(requireContext())

        binding.btnBack.setOnClickListener { mainFlowNavController?.popBackStack() }

        binding.btnSave.setOnClickListener {
            // TODO: business logic!
            CoroutineScope(Dispatchers.Main).launch {
                binding.loadingIndicator.show()
                delay(1500)
                binding.loadingIndicator.hide()
                requireContext().showToast("Berhasil disimpan")
                mainFlowNavController?.navigate(R.id.action_global_homeScreen)
            }
        }

        fruitsAdapterSetup()
    }

    private fun fruitsAdapterSetup() {
        adapter = FruitsAdapter(clickListener = FruitsListener {
            // TODO: pass data with viewModel navGraph scope
        })
        binding.rvFruits.adapter = adapter
        val fruits = localResourceData.dummyFruits
        CoroutineScope(Dispatchers.Main).launch {
            binding.loadingIndicator.show()
            delay(1500)
            adapter.submitList(fruits)
            binding.loadingIndicator.hide()
        }
    }
}
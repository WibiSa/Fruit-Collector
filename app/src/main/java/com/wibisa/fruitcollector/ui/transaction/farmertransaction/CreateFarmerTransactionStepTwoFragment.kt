package com.wibisa.fruitcollector.ui.transaction.farmertransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.util.LocalResourceData
import com.wibisa.fruitcollector.core.util.isNotNullOrEmpty
import com.wibisa.fruitcollector.databinding.FragmentCreateFarmerTransactionStepTwoBinding
import com.wibisa.fruitcollector.databinding.ItemTransFarmerCommodityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CreateFarmerTransactionStepTwoFragment : Fragment() {

    private lateinit var binding: FragmentCreateFarmerTransactionStepTwoBinding
    private lateinit var partialBinding: ItemTransFarmerCommodityBinding
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

        val dummyFruitComm = localResourceData.dummyCommoditiesForTransFarmer[0]

        partialBinding.apply {
            val fruitAndGrade = getString(
                R.string.fruit_name_and_grade,
                dummyFruitComm.commodityName,
                dummyFruitComm.grade
            )
            tvFruitNameAndGrade.text = fruitAndGrade
            tvFarmerName.text = dummyFruitComm.farmerName
            tvHarvestDate.text =
                getString(R.string.harvest_date_with_date, dummyFruitComm.harvestDate)
            tvStock.text = getString(R.string.stock_with_value, dummyFruitComm.stock)
        }

        binding.tvPriceTotal.text = "Rp 125.000"

        binding.btnSave.setOnClickListener {
            val isValid = binding.tfPrice.isNotNullOrEmpty(getString(R.string.required)) and
                    binding.tfQuantity.isNotNullOrEmpty(getString(R.string.required))

            if (isValid)
                CoroutineScope(Dispatchers.Main).launch {
                    binding.loadingIndicator.show()
                    delay(1500)
                    binding.loadingIndicator.hide()
                    save()
                }
        }
    }

    private fun save() {
        // TODO: save data farmer to server
        mainFlowNavController?.popBackStack(R.id.homeScreen, false)
    }

}
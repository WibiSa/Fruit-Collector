package com.wibisa.fruitcollector.ui.transaction.farmertransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.domain.model.TransactionFarmerCommodity
import com.wibisa.fruitcollector.core.util.rupiahCurrencyFormat
import com.wibisa.fruitcollector.databinding.FragmentFarmerTransactionDetailsBinding

class FarmerTransactionDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFarmerTransactionDetailsBinding
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val args: FarmerTransactionDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFarmerTransactionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        componentUiSetup()
    }

    private fun componentUiSetup() {
        binding.appbar.setNavigationOnClickListener {
            mainFlowNavController?.popBackStack()
        }

        bindFarmerTransaction(args.farmerTransaction)
    }

    private fun bindFarmerTransaction(data: TransactionFarmerCommodity) {
        binding.apply {
            tvTransactionCreateAt.text = getString(R.string.transaction_create_at, data.createdAt)
            tvFarmerName.text = data.farmerName
            tvFruitName.text = data.commodityName
            tvFruitWeight.text = getString(R.string.many_stock, data.stock)
            tvPrice.text = data.priceTotal.toString().rupiahCurrencyFormat()
            tvTreeBlossomDate.text = data.blossomsTreeDate
            tvHarvestDate.text = data.harvestDate
            tvFruitGrade.text = data.grade
            tvPricePerKg.text = data.pricePerKg.toString().rupiahCurrencyFormat()
        }
    }
}
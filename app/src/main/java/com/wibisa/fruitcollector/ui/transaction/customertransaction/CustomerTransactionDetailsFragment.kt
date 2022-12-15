package com.wibisa.fruitcollector.ui.transaction.customertransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.data.remote.response.CustomerTransaction
import com.wibisa.fruitcollector.core.util.BASE_URL
import com.wibisa.fruitcollector.core.util.rupiahCurrencyFormat
import com.wibisa.fruitcollector.core.util.showToast
import com.wibisa.fruitcollector.databinding.FragmentCustomerTransactionDetailsBinding

class CustomerTransactionDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCustomerTransactionDetailsBinding
    private val mainFlowNavController: NavController? by lazy { view?.findNavController() }
    private val args: CustomerTransactionDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCustomerTransactionDetailsBinding.inflate(inflater, container, false)
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

        bindCustomerTransaction(args.customerTransaction)
    }

    private fun bindCustomerTransaction(data: CustomerTransaction) {
        binding.apply {
            tvTransactionCreateAt.text = getString(R.string.transaction_create_at, data.createdAt)

            tvNoOrder.text = data.id
            tvFarmer.text = data.farmerTransaction.fruitCommodity.farmer.name
            tvFruit.text = data.farmerTransaction.fruitCommodity.fruit.name
            tvFruitGrade.text = data.farmerTransaction.fruitCommodity.fruitGrade
            tvQuantity.text = data.weight.toString()
            tvHarvestDate.text = data.farmerTransaction.fruitCommodity.harvestingDate

            tvBuyerName.text = data.receiverName
            tvBuyerPhone.text = data.customer.phoneNumber
            tvBuyerAddress.text = data.address
            tvDeliveryDate.text = data.shipingDate

            tvPurchaseItems.text = data.price.toString().rupiahCurrencyFormat()
            tvShippingCost.text = data.shipingPayment.toString().rupiahCurrencyFormat()
            tvTotalPrice.text = data.totalPayment.toString().rupiahCurrencyFormat()

            btnSendReceipt.setOnClickListener {
                val receiptLink = "$BASE_URL/${data.struckLink}"
                requireContext().showToast(receiptLink)
            }
        }
    }
}
package com.wibisa.fruitcollector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.data.remote.response.CustomerTransaction
import com.wibisa.fruitcollector.core.util.rupiahCurrencyFormat
import com.wibisa.fruitcollector.databinding.ItemCustomerTransactionBinding

class CustomerTransactionAdapter(
    private val context: Context,
    private val clickListener: CustomerTransactionListener
) :
    ListAdapter<CustomerTransaction, CustomerTransactionAdapter.ViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemCustomerTransactionBinding =
            ItemCustomerTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemCustomerTransactionBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null)
            holder.bind(item, clickListener)
    }

    inner class ViewHolder(private val binding: ItemCustomerTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CustomerTransaction, clickListener: CustomerTransactionListener) {
            binding.apply {
                val fruitAndGrade =
                    context.getString(
                        R.string.fruit_name_and_grade,
                        item.farmerTransaction.fruitCommodity.fruit.name,
                        item.farmerTransaction.fruitCommodity.fruitGrade
                    )
                tvFruitNameAndGrade.text = fruitAndGrade
                tvFarmerName.text = item.farmerTransaction.fruitCommodity.farmer.name
                tvHarvestDate.text =
                    context.getString(
                        R.string.harvest_date_with_date,
                        item.farmerTransaction.fruitCommodity.harvestingDate
                    )
                tvStock.text =
                    context.getString(R.string.stock_with_value, item.weight)
                val price = item.price.toString().rupiahCurrencyFormat()
                tvPricePerKg.text = context.getString(R.string.price_per_kg_with_value, price)
            }

            itemView.setOnClickListener { clickListener.onClick(item) }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<CustomerTransaction>() {
            override fun areItemsTheSame(
                oldItem: CustomerTransaction,
                newItem: CustomerTransaction
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CustomerTransaction,
                newItem: CustomerTransaction
            ): Boolean = oldItem == newItem

        }
    }
}

class CustomerTransactionListener(val clickListener: (customerTransaction: CustomerTransaction) -> Unit) {
    fun onClick(customerTransaction: CustomerTransaction) =
        clickListener(customerTransaction)
}
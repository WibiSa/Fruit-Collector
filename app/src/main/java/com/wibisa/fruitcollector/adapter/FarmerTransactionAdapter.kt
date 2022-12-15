package com.wibisa.fruitcollector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.domain.model.TransactionFarmerCommodity
import com.wibisa.fruitcollector.core.util.rupiahCurrencyFormat
import com.wibisa.fruitcollector.databinding.ItemFarmerTransactionBinding

class FarmerTransactionAdapter(
    private val context: Context,
    private val clickListener: FarmerTransactionListener
) :
    ListAdapter<TransactionFarmerCommodity, FarmerTransactionAdapter.ViewHolder>(
        COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemFarmerTransactionBinding =
            ItemFarmerTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemFarmerTransactionBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val farmerTransaction = getItem(position)
        if (farmerTransaction != null)
            holder.bind(farmerTransaction, clickListener)
    }

    inner class ViewHolder(private val binding: ItemFarmerTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: TransactionFarmerCommodity,
            clickListener: FarmerTransactionListener
        ) {
            binding.apply {
                val fruitAndGrade =
                    context.getString(R.string.fruit_name_and_grade, item.commodityName, item.grade)
                tvFruitNameAndGrade.text = fruitAndGrade
                tvFarmerName.text = item.farmerName
                tvHarvestDate.text =
                    context.getString(R.string.harvest_date_with_date, item.harvestDate)
                tvStock.text = context.getString(R.string.stock_with_value, item.stock)
                val price = item.pricePerKg.toString().rupiahCurrencyFormat()
                tvPricePerKg.text = context.getString(R.string.price_per_kg_from_farmer_with_value, price)
            }

            itemView.setOnClickListener { clickListener.onClick(item) }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<TransactionFarmerCommodity>() {
            override fun areItemsTheSame(
                oldItem: TransactionFarmerCommodity,
                newItem: TransactionFarmerCommodity
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: TransactionFarmerCommodity,
                newItem: TransactionFarmerCommodity
            ): Boolean =
                oldItem == newItem
        }
    }
}

class FarmerTransactionListener(val clickListener: (farmerTransaction: TransactionFarmerCommodity) -> Unit) {
    fun onClick(farmerTransaction: TransactionFarmerCommodity) =
        clickListener(farmerTransaction)
}
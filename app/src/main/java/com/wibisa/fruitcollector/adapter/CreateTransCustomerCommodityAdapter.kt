package com.wibisa.fruitcollector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.domain.model.TransactionFarmerCommodity
import com.wibisa.fruitcollector.databinding.ItemCreateCustomerTransactionBinding

class CreateTransCustomerCommodityAdapter(
    private val context: Context,
    private val clickListener: CreateTransCustomerCommodityListener
) :
    ListAdapter<TransactionFarmerCommodity, CreateTransCustomerCommodityAdapter.ViewHolder>(
        COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemTransCustomerCommodityBinding =
            ItemCreateCustomerTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(itemTransCustomerCommodityBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commodity = getItem(position)
        if (commodity != null)
            holder.bind(commodity, clickListener)
    }

    inner class ViewHolder(private val binding: ItemCreateCustomerTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: TransactionFarmerCommodity,
            clickListener: CreateTransCustomerCommodityListener
        ) {
            binding.apply {
                val fruitAndGrade =
                    context.getString(R.string.fruit_name_and_grade, item.commodityName, item.grade)
                tvFruitNameAndGrade.text = fruitAndGrade
                tvFarmerName.text = item.farmerName
                tvHarvestDate.text =
                    context.getString(R.string.harvest_date_with_date, item.harvestDate)
                tvStock.text = context.getString(R.string.stock_with_value, item.stock)
                tvPricePerKg.text = context.getString(R.string.price_per_kg_from_farmer, item.pricePerKg)
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

class CreateTransCustomerCommodityListener(val clickListener: (transFarmerCommodity: TransactionFarmerCommodity) -> Unit) {
    fun onClick(transFarmerCommodity: TransactionFarmerCommodity) =
        clickListener(transFarmerCommodity)
}
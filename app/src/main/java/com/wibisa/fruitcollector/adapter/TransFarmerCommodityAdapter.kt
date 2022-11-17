package com.wibisa.fruitcollector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.domain.model.Commodity
import com.wibisa.fruitcollector.core.util.hide
import com.wibisa.fruitcollector.core.util.show
import com.wibisa.fruitcollector.databinding.ItemTransFarmerCommodityBinding

class TransFarmerCommodityAdapter(
    private val context: Context,
    private val clickListener: CommodityTransListener
) :
    ListAdapter<Commodity, TransFarmerCommodityAdapter.CommodityViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommodityViewHolder {
        val itemTransFarmerCommodityBinding =
            ItemTransFarmerCommodityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CommodityViewHolder(itemTransFarmerCommodityBinding)
    }

    override fun onBindViewHolder(holder: CommodityViewHolder, position: Int) {
        val commodity = getItem(position)
        if (commodity != null)
            holder.bind(commodity, clickListener)
    }

    inner class CommodityViewHolder(private val binding: ItemTransFarmerCommodityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Commodity, clickListener: CommodityTransListener) {
            binding.apply {
                val fruitAndGrade =
                    context.getString(R.string.fruit_name_and_grade, item.commodityName, item.grade)
                tvFruitNameAndGrade.text = fruitAndGrade
                tvFarmerName.text = item.farmerName
                tvHarvestDate.text =
                    context.getString(R.string.harvest_date_with_date, item.harvestDate)
                tvStock.text = context.getString(R.string.stock_with_value, item.stock)
                if (item.isValid == 1) tvValidationStatus.show() else tvValidationStatus.hide()
            }

            itemView.setOnClickListener { clickListener.onClick(item) }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Commodity>() {
            override fun areItemsTheSame(oldItem: Commodity, newItem: Commodity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Commodity, newItem: Commodity): Boolean =
                oldItem == newItem
        }
    }
}

class CommodityTransListener(val clickListener: (commodity: Commodity) -> Unit) {
    fun onClick(commodity: Commodity) = clickListener(commodity)
}
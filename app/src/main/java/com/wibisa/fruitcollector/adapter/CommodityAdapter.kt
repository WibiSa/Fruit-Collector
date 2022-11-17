package com.wibisa.fruitcollector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wibisa.fruitcollector.core.domain.model.Commodity
import com.wibisa.fruitcollector.core.util.hide
import com.wibisa.fruitcollector.core.util.show
import com.wibisa.fruitcollector.databinding.ItemFruitCommodityBinding

class CommodityAdapter(private val clickListener: CommodityListener) :
    ListAdapter<Commodity, CommodityAdapter.CommodityViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommodityViewHolder {
        val itemFruitCommodityBinding =
            ItemFruitCommodityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommodityViewHolder(itemFruitCommodityBinding)
    }

    override fun onBindViewHolder(holder: CommodityViewHolder, position: Int) {
        val commodity = getItem(position)
        if (commodity != null)
            holder.bind(commodity, clickListener)
    }

    class CommodityViewHolder(private val binding: ItemFruitCommodityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Commodity, clickListener: CommodityListener) {
            binding.apply {
                tvFruitName.text = item.commodityName
                tvFarmerName.text = item.farmerName
                tvDate.text = item.harvestDate
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

class CommodityListener(val clickListener: (commodity: Commodity) -> Unit) {
    fun onClick(commodity: Commodity) = clickListener(commodity)
}
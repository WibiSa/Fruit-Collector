package com.wibisa.fruitcollector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wibisa.fruitcollector.core.domain.model.Farmer
import com.wibisa.fruitcollector.core.util.hide
import com.wibisa.fruitcollector.core.util.show
import com.wibisa.fruitcollector.databinding.ItemFarmerBinding

class FarmersAdapter(
    private val isSuffixImageShow: Boolean = true,
    private val clickListener: FarmersListener
) :
    ListAdapter<Farmer, FarmersAdapter.FarmersViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmersViewHolder {
        val itemFarmerBinding =
            ItemFarmerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FarmersViewHolder(itemFarmerBinding)
    }

    override fun onBindViewHolder(holder: FarmersViewHolder, position: Int) {
        val farmer = getItem(position)
        if (farmer != null)
            holder.bind(farmer, clickListener, isSuffixImageShow)
    }

    class FarmersViewHolder(private val binding: ItemFarmerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Farmer, clickListener: FarmersListener, isSuffixImageShow: Boolean) {
            binding.tvName.text = item.name
            if (isSuffixImageShow) binding.imgEnd.show() else binding.imgEnd.hide()
            itemView.setOnClickListener { clickListener.onClick(item) }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Farmer>() {
            override fun areItemsTheSame(oldItem: Farmer, newItem: Farmer): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Farmer, newItem: Farmer): Boolean =
                oldItem == newItem
        }
    }
}

class FarmersListener(val clickListener: (farmer: Farmer) -> Unit) {
    fun onClick(farmer: Farmer) = clickListener(farmer)
}
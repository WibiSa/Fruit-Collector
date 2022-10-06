package com.wibisa.fruitcollector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wibisa.fruitcollector.core.domain.model.Fruit
import com.wibisa.fruitcollector.databinding.ItemFruitBinding

class FruitsAdapter(private val clickListener: FruitsListener) :
    ListAdapter<Fruit, FruitsAdapter.FruitsViewHolder>(COMPARATOR) {

    private var checkedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitsViewHolder {
        val itemFruitBinding =
            ItemFruitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FruitsViewHolder(itemFruitBinding)
    }

    override fun onBindViewHolder(holder: FruitsViewHolder, position: Int) {
        val fruit = getItem(position)
        if (fruit != null)
            holder.bind(fruit, clickListener)
    }

    inner class FruitsViewHolder(private val binding: ItemFruitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Fruit, clickListener: FruitsListener) {

            if (checkedPosition == -1) {
                // do nothing
            } else {
                binding.cardFruit.isChecked = checkedPosition == adapterPosition
            }

            binding.tvFruitName.text = item.name

            itemView.setOnClickListener {
                binding.cardFruit.isChecked = true
                if (checkedPosition != adapterPosition) {
                    notifyItemChanged(checkedPosition)
                    checkedPosition = adapterPosition
                }

                clickListener.onClick(item)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Fruit>() {
            override fun areItemsTheSame(oldItem: Fruit, newItem: Fruit): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Fruit, newItem: Fruit): Boolean =
                oldItem == newItem
        }
    }
}

class FruitsListener(val clickListener: (fruit: Fruit) -> Unit) {
    fun onClick(fruit: Fruit) = clickListener(fruit)
}
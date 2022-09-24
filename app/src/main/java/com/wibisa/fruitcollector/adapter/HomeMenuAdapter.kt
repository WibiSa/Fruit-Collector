package com.wibisa.fruitcollector.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wibisa.fruitcollector.core.domain.model.HomeMenu
import com.wibisa.fruitcollector.databinding.ItemMenuHomeBinding

class HomeMenuAdapter : ListAdapter<HomeMenu, HomeMenuAdapter.HomeMenuViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMenuViewHolder {
        val itemMenuHomeBinding =
            ItemMenuHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeMenuViewHolder(itemMenuHomeBinding)
    }

    override fun onBindViewHolder(holder: HomeMenuViewHolder, position: Int) {
        val menu = getItem(position)
        if (menu != null)
            holder.bind(menu)
    }

    class HomeMenuViewHolder(private val binding: ItemMenuHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeMenu) {
            binding.tvMenu.text = item.name
            Glide.with(itemView).load(item.image).into(binding.imgMenu)

            itemView.setOnClickListener { Log.d("HomeMenuAdapter", "${item.name} tapped!") }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<HomeMenu>() {
            override fun areItemsTheSame(oldItem: HomeMenu, newItem: HomeMenu): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: HomeMenu, newItem: HomeMenu): Boolean =
                oldItem == newItem
        }
    }
}
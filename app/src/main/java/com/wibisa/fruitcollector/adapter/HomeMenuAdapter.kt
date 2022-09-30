package com.wibisa.fruitcollector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wibisa.fruitcollector.core.domain.model.HomeMenu
import com.wibisa.fruitcollector.databinding.ItemMenuHomeBinding

class HomeMenuAdapter(private val clickListener: HomeMenuListener) :
    ListAdapter<HomeMenu, HomeMenuAdapter.HomeMenuViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMenuViewHolder {
        val itemMenuHomeBinding =
            ItemMenuHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeMenuViewHolder(itemMenuHomeBinding)
    }

    override fun onBindViewHolder(holder: HomeMenuViewHolder, position: Int) {
        val menu = getItem(position)
        if (menu != null)
            holder.bind(menu, clickListener)
    }

    class HomeMenuViewHolder(private val binding: ItemMenuHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeMenu, clickListener: HomeMenuListener) {
            binding.tvMenu.text = item.name
            Glide.with(itemView).load(item.image).into(binding.imgMenu)

            itemView.setOnClickListener { clickListener.onClick(item) }
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

class HomeMenuListener(val clickListener: (homeMenu: HomeMenu) -> Unit) {
    fun onClick(homeMenu: HomeMenu) = clickListener(homeMenu)
}
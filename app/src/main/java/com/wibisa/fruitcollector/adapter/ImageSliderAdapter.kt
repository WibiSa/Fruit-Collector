package com.wibisa.fruitcollector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import com.wibisa.fruitcollector.adapter.ImageSliderAdapter.ImageSliderViewHolder
import com.wibisa.fruitcollector.core.domain.model.ImageBanner
import com.wibisa.fruitcollector.databinding.ItemImageSliderBinding

class ImageSliderAdapter(imageBanners: List<ImageBanner>) :
    SliderViewAdapter<ImageSliderViewHolder>() {

    private var sliders = imageBanners

    override fun getCount(): Int = sliders.size

    override fun onCreateViewHolder(parent: ViewGroup?): ImageSliderViewHolder {
        val itemImageSliderBinding =
            ItemImageSliderBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)
        return ImageSliderViewHolder(itemImageSliderBinding)
    }

    override fun onBindViewHolder(viewHolder: ImageSliderViewHolder?, position: Int) {
        val imageBanner = sliders[position]

        viewHolder?.bind(imageBanner)
    }

    class ImageSliderViewHolder(private val binding: ItemImageSliderBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {

        fun bind(item: ImageBanner) {
            Glide.with(itemView).load(item.image).fitCenter().into(binding.imgBanner)
        }
    }
}
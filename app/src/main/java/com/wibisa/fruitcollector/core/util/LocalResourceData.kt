package com.wibisa.fruitcollector.core.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.domain.model.Farmer
import com.wibisa.fruitcollector.core.domain.model.HomeMenu
import com.wibisa.fruitcollector.core.domain.model.ImageBanner

class LocalResourceData(context: Context) {
    val homeMenu = listOf(
        HomeMenu("Tambah Petani", ContextCompat.getDrawable(context, R.drawable.ic_user_plus)!!),
        HomeMenu("Catat Komoditas", ContextCompat.getDrawable(context, R.drawable.ic_fruit_bowl)!!),
        HomeMenu("Buat Transaksi", ContextCompat.getDrawable(context, R.drawable.ic_exchange)!!),
        HomeMenu("Petani", ContextCompat.getDrawable(context, R.drawable.ic_farmer)!!),
        HomeMenu("Komoditas", ContextCompat.getDrawable(context, R.drawable.ic_basket)!!),
        HomeMenu("Transaksi Petani", ContextCompat.getDrawable(context, R.drawable.ic_buy)!!),
        HomeMenu(
            "Transaksi Pelanggan", ContextCompat.getDrawable(context, R.drawable.ic_buy)!!
        )
    )

    val imageBanners = listOf(
        ImageBanner(ContextCompat.getDrawable(context, R.drawable.banner_sample)!!),
        ImageBanner(ContextCompat.getDrawable(context, R.drawable.banner_sample)!!),
        ImageBanner(ContextCompat.getDrawable(context, R.drawable.banner_sample)!!)
    )

    val dummyFarmers = listOf(
        Farmer("1", "Budi Suharto"),
        Farmer("2", "Budi Harto"),
        Farmer("3", "Cipta Rama"),
        Farmer("4", "Suharto Aji")
    )
}
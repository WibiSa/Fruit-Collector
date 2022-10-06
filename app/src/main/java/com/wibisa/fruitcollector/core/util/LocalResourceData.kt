package com.wibisa.fruitcollector.core.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.wibisa.fruitcollector.R
import com.wibisa.fruitcollector.core.domain.model.*

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

    val dummyFruits = listOf(
        Fruit("1", "Durian"),
        Fruit("2", "Manggis"),
        Fruit("3", "Pepaya"),
        Fruit("4", "Rambutan"),
    )

    val dummyCommodities = listOf(
        Commodity("1", "Manggis", "Budi", "04-06-2022", false),
        Commodity("2", "Manggis", "Sutris", "04-06-2022", true),
        Commodity("3", "Rambutan", "Budi", "04-05-2022", false),
        Commodity("4", "Manggis", "Beni", "04-04-2022", true),
        Commodity("5", "Durian", "Zulkifli", "04-03-2022", true)
    )

    val dummyFruitGrades = listOf("A","AA","AAA")
}
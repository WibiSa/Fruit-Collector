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

    val dummyFarmers = listOf<Farmer>(
//        Farmer("1", "Budi Suharto"),
//        Farmer("2", "Budi Harto"),
//        Farmer("3", "Cipta Rama"),
//        Farmer("4", "Suharto Aji")
    )

    val dummyFruits = listOf(
        Fruit("1", "Durian"),
        Fruit("2", "Manggis"),
        Fruit("3", "Pepaya"),
        Fruit("4", "Rambutan"),
    )

    val dummyCommodities = listOf(
        Commodity(
            "1",
            "Manggis",
            "Budi",
            "04-06-2022",
            isValid = false,
            grade = null,
            harvestDate = null,
            stock = null
        ),
        Commodity(
            "2",
            "Manggis",
            "Sutris",
            "04-06-2022",
            isValid = true,
            grade = null,
            harvestDate = null,
            stock = null
        ),
        Commodity(
            "3",
            "Rambutan",
            "Budi",
            "04-05-2022",
            isValid = true,
            grade = null,
            harvestDate = null,
            stock = null
        ),
        Commodity(
            "4",
            "Manggis",
            "Beni",
            "04-04-2022",
            isValid = true,
            grade = null,
            harvestDate = null,
            stock = null
        ),
        Commodity(
            "5",
            "Durian",
            "Zulkifli",
            "04-03-2022",
            isValid = true,
            grade = null,
            harvestDate = null,
            stock = null
        )
    )

    val dummyCommoditiesForTransFarmer = listOf(
        Commodity(
            "1",
            "Manggis",
            "Budi",
            "04-06-2022",
            isValid = true,
            grade = "AAA",
            harvestDate = "02-10-2022",
            stock = 150
        ),
        Commodity(
            "2",
            "Manggis",
            "Sutris",
            "04-06-2022",
            isValid = true,
            grade = "AAA",
            harvestDate = "02-10-2022",
            stock = 50
        ),
        Commodity(
            "3",
            "Rambutan",
            "Budi",
            "04-05-2022",
            isValid = true,
            grade = "A",
            harvestDate = "02-10-2022",
            stock = 95
        ),
        Commodity(
            "4",
            "Manggis",
            "Beni",
            "04-04-2022",
            isValid = true,
            grade = "AA",
            harvestDate = "02-10-2022",
            stock = 80
        )
    )

    val dummyTransFarmer = listOf(
        TransactionFarmerCommodity(
            "1",
            "Manggis",
            "Budi",
            "04-04-2022",
            grade = "AA",
            harvestDate = "02-10-2022",
            stock = 80,
            pricePerKg = 16000
        ),
        TransactionFarmerCommodity(
            "2",
            "Manggis",
            "Beni",
            "04-04-2022",
            grade = "AA",
            harvestDate = "02-10-2022",
            stock = 80,
            pricePerKg = 16000
        ),
        TransactionFarmerCommodity(
            "3",
            "Manggis",
            "Carlie",
            "04-04-2022",
            grade = "AA",
            harvestDate = "02-10-2022",
            stock = 80,
            pricePerKg = 16000
        )
    )

    val dummyFruitGrades = listOf("A", "AA", "AAA")
}
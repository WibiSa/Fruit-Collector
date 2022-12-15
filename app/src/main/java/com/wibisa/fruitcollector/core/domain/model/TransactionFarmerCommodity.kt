package com.wibisa.fruitcollector.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionFarmerCommodity(
    val id: String,
    val commodityName: String,
    val farmerName: String,
    val grade: String,
    val blossomsTreeDate: String,
    val harvestDate: String,
    val stock: Int,
    val pricePerKg: Int,
    val priceTotal: Int,
    val createdAt: String,
    ): Parcelable
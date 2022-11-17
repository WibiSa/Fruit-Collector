package com.wibisa.fruitcollector.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Commodity(
    val id: String,
    val commodityName: String,
    val farmerName: String,
    val blossomsTreeDate: String?,
    val grade: String?,
    val harvestDate: String?,
    val stock: Int?,
    val isValid: Int // 1 = true, 0 = false
) : Parcelable
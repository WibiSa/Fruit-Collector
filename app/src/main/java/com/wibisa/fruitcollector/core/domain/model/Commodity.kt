package com.wibisa.fruitcollector.core.domain.model

data class Commodity(
    val id: String,
    val commodityName: String,
    val farmerName: String,
    val recordDate: String,
    val grade: String?,
    val harvestDate: String?,
    val stock: Int?,
    val isValid: Boolean,

    // TODO: masih ada data lain disini!
)
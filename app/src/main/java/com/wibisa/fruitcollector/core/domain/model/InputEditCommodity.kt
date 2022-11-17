package com.wibisa.fruitcollector.core.domain.model

data class InputEditCommodity(
    val idCommodity: String,
    val blossomsTreeDate: String = "",
    val harvestingDate: String = "",
    val fruitGrade: String = "",
    val stock: Int = 0
)

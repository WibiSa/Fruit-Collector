package com.wibisa.fruitcollector.core.domain.model

data class InputAddFarmerTransaction(
    val idCommodity: String,
    val quantity: Int = 0,
    val price: Int = 0,
    val totalPrice: Int = 0
)

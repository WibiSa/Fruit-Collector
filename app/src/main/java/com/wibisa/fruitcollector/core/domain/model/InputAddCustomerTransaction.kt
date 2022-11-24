package com.wibisa.fruitcollector.core.domain.model

data class InputAddCustomerTransaction(
    val farmerTransactionId: String,
    val quantity: Int,
    val price: Int,
    val shippingPrice: Int,
    val totalPrice: Int,
    val shippingDate: String,
    val address: String,
    val buyerName: String,
    val phone: String
)

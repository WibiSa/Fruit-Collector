package com.wibisa.fruitcollector.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListFarmerTransactionNetwork(
    val data: FarmerTransactionData,
    val meta: Meta
)

data class FarmerTransactionData(
    @SerializedName("farmer_transaction")
    val farmerTransaction: List<FarmerTransaction>
)

data class FarmerTransaction(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("fruit_commodity")
    val fruitCommodity: Commodity,
    @SerializedName("fruit_commodity_id")
    val fruitCommodityId: String,
    val id: String,
    val payment: Int,
    @SerializedName("price_kg")
    val priceKg: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    val weight: Int,
    @SerializedName("weight_selled")
    val soldWeight: Int?
)
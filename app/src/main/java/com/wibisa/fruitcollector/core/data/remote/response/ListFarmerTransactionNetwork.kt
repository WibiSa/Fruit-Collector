package com.wibisa.fruitcollector.core.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ListFarmerTransactionNetwork(
    val data: List<FarmerTransactionData>,
    val meta: Meta
)

@Parcelize
data class FarmerTransactionData(
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
): Parcelable
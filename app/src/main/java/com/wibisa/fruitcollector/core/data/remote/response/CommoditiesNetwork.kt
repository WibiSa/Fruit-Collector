package com.wibisa.fruitcollector.core.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CommoditiesNetwork(
    val meta: Meta,
    val `data`: CommoditiesData
)

data class CommoditiesData(
    @SerializedName("fruit_comodity")
    val commodity: List<Commodity>
)

@Parcelize
data class Commodity(
    val id: String,
    @SerializedName("farmer_id")
    val farmerId: String,
    @SerializedName("collector_id")
    val collectorId: String,
    @SerializedName("fruit_id")
    val fruitId: String,
    @SerializedName("blossoms_tree_date")
    val blossomsTreeDate: String,
    @SerializedName("harvesting_date")
    val harvestingDate: String,
    @SerializedName("fruit_grade")
    val fruitGrade: String,
    val verified: Int,
    @SerializedName("verfied_date")
    val verfiedDate: String,
    val weight: Int,
    @SerializedName("price_kg")
    val priceKg: Int?,
    @SerializedName("weight_selled")
    val weightSelled: Int?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val farmer: FarmersDataX,
    val fruit: FruitData
): Parcelable
package com.wibisa.fruitcollector.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class CommoditiesNetwork(
    val meta: Meta,
    val `data`: CommoditiesData
)

data class CommoditiesData(
    @SerializedName("fruit_comodity")
    val commodity: List<Commodity>
)

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
    val priceKg: Any?,
    @SerializedName("weight_selled")
    val weightSelled: Any?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val farmer: Farmer,
    val fruit: Fruit
)
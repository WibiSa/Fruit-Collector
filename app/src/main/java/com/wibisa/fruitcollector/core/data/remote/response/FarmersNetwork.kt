package com.wibisa.fruitcollector.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class FarmersNetwork(
    val meta: Meta,
    val `data`: FarmersData
)

data class FarmersData(
    val farmer: List<Farmer>
)

data class Farmer(
    val id: String,
    val name: String,
    @SerializedName("land_location")
    val landLocation: String,
    @SerializedName("number_tree")
    val numberTree: Int,
    @SerializedName("estimation_production")
    val estimationProduction: Int,
    @SerializedName("land_size")
    val landSize: Double,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
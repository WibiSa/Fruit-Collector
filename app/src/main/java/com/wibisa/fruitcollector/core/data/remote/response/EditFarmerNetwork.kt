package com.wibisa.fruitcollector.core.data.remote.response

import com.google.gson.annotations.SerializedName
import com.wibisa.fruitcollector.core.domain.model.Farmer

data class EditFarmerNetwork(
    val meta: Meta,
    val `data`: FarmerDataUpdated
)

data class FarmerDataUpdated(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
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

fun FarmerDataUpdated.asDomainModel(): Farmer =
    Farmer(
        id = id,
        name = name,
        landLocation = landLocation,
        landSize = landSize,
        estimationProduction = estimationProduction,
        numberTree = numberTree
    )

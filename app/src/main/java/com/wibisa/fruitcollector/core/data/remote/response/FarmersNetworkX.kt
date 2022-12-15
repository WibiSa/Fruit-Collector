package com.wibisa.fruitcollector.core.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FarmersNetworkX(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("data")
    val `data`: List<FarmersDataX>
)

@Parcelize
data class FarmersDataX(
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
): Parcelable
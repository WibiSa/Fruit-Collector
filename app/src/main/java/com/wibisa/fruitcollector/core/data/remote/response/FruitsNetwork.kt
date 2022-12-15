package com.wibisa.fruitcollector.core.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FruitsNetwork(
    val meta: Meta,
    val `data`: List<FruitData>
)

@Parcelize
data class FruitData(
    val id: String,
    val name: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
) : Parcelable
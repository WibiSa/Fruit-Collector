package com.wibisa.fruitcollector.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class FruitsNetwork(
    val meta: Meta,
    val `data`: FruitData
)

data class FruitData(
    val fruits: List<Fruit>
)

data class Fruit(
    val id: String,
    val name: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
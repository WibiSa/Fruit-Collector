package com.wibisa.fruitcollector.core.data.remote.response

import com.google.gson.annotations.SerializedName
import com.wibisa.fruitcollector.core.domain.model.Register

data class RegisterNetworkX(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("data")
    val `data`: List<UserDataFromRegisterX>
)

data class UserDataFromRegisterX(
    val name: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val email: String,
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    val token: String
)

fun UserDataFromRegisterX.asDomainModel(): Register =
    Register(id = id, name = name, token = token)
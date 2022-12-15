package com.wibisa.fruitcollector.core.data.remote.response

import com.google.gson.annotations.SerializedName
import com.wibisa.fruitcollector.core.domain.model.Login

data class LoginNetworkX(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("data")
    val `data`: List<UserDataFromLoginX>
)

data class UserDataFromLoginX(
    val id: String,
    val name: String,
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any?,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val token: String
)

fun UserDataFromLoginX.asDomainModel(): Login =
    Login(id = id, name = name, token = token)
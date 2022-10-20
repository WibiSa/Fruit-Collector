package com.wibisa.fruitcollector.core.data.remote.response

import com.google.gson.annotations.SerializedName
import com.wibisa.fruitcollector.core.domain.model.Login

data class LoginNetwork(
    val meta: Meta,
    val `data`: LoginData
)

data class LoginData(
    val message: String,
    val user: UserDataFromLogin,
    val token: String
)

data class UserDataFromLogin(
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
    val updatedAt: String
)

fun LoginData.asDomainModel(): Login =
    Login(id = user.id, name = user.name, token = token)
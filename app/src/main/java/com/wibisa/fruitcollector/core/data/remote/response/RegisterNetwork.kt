package com.wibisa.fruitcollector.core.data.remote.response

import com.google.gson.annotations.SerializedName
import com.wibisa.fruitcollector.core.domain.model.Register

data class RegisterNetwork(
    val meta: Meta,
    val `data`: RegisterData
)

data class RegisterData(
    val message: String,
    val user: UserDataFromRegister,
    val token: String
)

data class UserDataFromRegister(
    val name: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val email: String,
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String
)

fun RegisterData.asDomainModel(): Register =
    Register(id = user.id, name = user.name, token = token)
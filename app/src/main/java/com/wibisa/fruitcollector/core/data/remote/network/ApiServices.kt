package com.wibisa.fruitcollector.core.data.remote.network

import com.wibisa.fruitcollector.core.data.remote.response.LoginNetwork
import com.wibisa.fruitcollector.core.data.remote.response.LogoutNetwork
import com.wibisa.fruitcollector.core.data.remote.response.RegisterNetwork
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiServices {

    @FormUrlEncoded
    @POST("api/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("phone_number") phone: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirm: String
    ): RegisterNetwork

    @FormUrlEncoded
    @POST("api/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginNetwork

    @POST("api/logout")
    suspend fun logout(
        @Header("Authorization") token: String
    ): LogoutNetwork
}
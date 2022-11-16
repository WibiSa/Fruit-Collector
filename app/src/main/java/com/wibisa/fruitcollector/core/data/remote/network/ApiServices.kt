package com.wibisa.fruitcollector.core.data.remote.network

import com.wibisa.fruitcollector.core.data.remote.response.*
import retrofit2.http.*

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

    @FormUrlEncoded
    @POST("api/collector/farmer")
    suspend fun addFarmer(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("land_location") landLocation: String,
        @Field("number_tree") numberOfTree: Int,
        @Field("estimation_production") estimationProduction: Int,
        @Field("land_size") landSize: Float
    ): AddFarmerNetwork

    @GET("api/collector/farmer")
    suspend fun getFarmers(
        @Header("Authorization") token: String
    ): FarmersNetwork

    @FormUrlEncoded
    @PUT("api/collector/farmer/{id_farmer}")
    suspend fun editFarmer(
        @Header("Authorization") token: String,
        @Path("id_farmer") idFarmer: String,
        @Field("name") name: String,
        @Field("land_location") landLocation: String,
        @Field("number_tree") numberOfTree: Int,
        @Field("estimation_production") estimationProduction: Int,
        @Field("land_size") landSize: Float
    ): EditFarmerNetwork

    @DELETE("api/collector/farmer/{id_farmer}")
    suspend fun deleteFarmer(
        @Header("Authorization") token: String,
        @Path("id_farmer") idFarmer: String
    ): DeleteFarmerNetwork

    @GET("api/collector/fruit")
    suspend fun getFruits(
        @Header("Authorization") token: String
    ): FruitsNetwork

    @FormUrlEncoded
    @POST("api/collector/comodity")
    suspend fun addCommodity(
        @Header("Authorization") token: String,
        @Field("farmer_id") idFarmer: String,
        @Field("fruit_id") idFruit: String
    ): AddCommodityNetwork

    @GET("api/collector/comodity")
    suspend fun getCommodities(
        @Header("Authorization") token: String
    ): CommoditiesNetwork
}
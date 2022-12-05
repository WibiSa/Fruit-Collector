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

    @FormUrlEncoded
    @PUT("/api/collector/comodity/{id_commodity}")
    suspend fun editCommodity(
        @Header("Authorization") token: String,
        @Path("id_commodity") idCommodity: String,
        @Field("blossoms_tree_date") blossomsTreeDate: String,
        @Field("harvesting_date") harvestingDate: String,
        @Field("fruit_grade") fruitGrade: String,
        @Field("weight") stock: Int
    ): EditCommodityNetwork

    @PUT("api/collector/comodity/verify/{id_commodity}")
    suspend fun verifyCommodity(
        @Header("Authorization") token: String,
        @Path("id_commodity") idCommodity: String
    ): VerifyCommodityNetwork

    @DELETE("/api/collector/comodity/{id_commodity}")
    suspend fun deleteCommodity(
        @Header("Authorization") token: String,
        @Path("id_commodity") idCommodity: String
    ): DeleteCommodityNetwork

    @GET("api/collector/comodity/list/verified")
    suspend fun getVerifiedCommodities(
        @Header("Authorization") token: String
    ): CommoditiesNetwork

    @FormUrlEncoded
    @POST("api/collector/transaction/farmer")
    suspend fun createFarmerTransaction(
        @Header("Authorization") token: String,
        @Field("fruit_comodity_id") idCommodity: String,
        @Field("weight") quantity: Int,
        @Field("price") price: Int,
        @Field("price_total") totalPrice: Int
    ): AddFarmerTransactionNetwork

    @GET("api/collector/transaction/farmer")
    suspend fun getListFarmerTransaction(
        @Header("Authorization") token: String
    ): ListFarmerTransactionNetwork

    @FormUrlEncoded
    @POST("api/collector/transaction/customer")
    suspend fun createCustomerTransaction(
        @Header("Authorization") token: String,
        @Field("farmer_transaction_id") farmerTransactionId: String,
        @Field("weight") quantity: Int,
        @Field("price") price: Int,
        @Field("shiping_payment") shippingPrice: Int,
        @Field("total_payment") totalPrice: Int,
        @Field("shiping_date") shippingDate: String,
        @Field("address") address: String,
        @Field("receiver_name") buyerName: String,
        @Field("phone_number") phone: String
    ): AddCustomerTransactionNetwork

    @GET("api/collector/transaction/customer")
    suspend fun getListCustomerTransaction(
        @Header("Authorization") token: String
    ): ListCustomerTransactionNetwork
}
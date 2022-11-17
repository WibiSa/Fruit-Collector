package com.wibisa.fruitcollector.core.data.repository

import com.wibisa.fruitcollector.core.data.remote.network.ApiServices
import com.wibisa.fruitcollector.core.domain.model.Commodity
import com.wibisa.fruitcollector.core.domain.model.InputAddFarmerTransaction
import com.wibisa.fruitcollector.core.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(private val api: ApiServices) {

    suspend fun getVerifiedCommodities(token: String): ApiResult<List<Commodity>> {
        try {
            val response = api.getVerifiedCommodities(token = token.tokenFormat())
            return when (response.meta.status) {
                API_RESPONSE_SUCCESS -> {
                    val commodities =
                        DataMapper.mapCommodityNetworkToDomain(response.data.commodity)
                    ApiResult.Success(commodities)
                }
                API_RESPONSE_FAILED -> {
                    ApiResult.Error(response.meta.message)
                }
                else -> {
                    ApiResult.Empty
                }
            }
        } catch (e: Exception) {
            return ApiResult.Error(e.message.toString())
        }
    }

    suspend fun createFarmerTransaction(
        token: String,
        inputAddFarmerTransaction: InputAddFarmerTransaction
    ): ApiResult<String> {
        try {
            val response = api.createFarmerTransaction(
                token = token.tokenFormat(),
                idCommodity = inputAddFarmerTransaction.idCommodity,
                quantity = inputAddFarmerTransaction.quantity,
                price = inputAddFarmerTransaction.price,
                totalPrice = inputAddFarmerTransaction.totalPrice
            )
            return when (response.meta.status) {
                API_RESPONSE_SUCCESS -> {
                    ApiResult.Success(response.meta.message)
                }
                API_RESPONSE_FAILED -> {
                    ApiResult.Error(response.meta.message)
                }
                else -> {
                    ApiResult.Empty
                }
            }
        } catch (e: Exception) {
            return ApiResult.Error(e.message.toString())
        }
    }
}
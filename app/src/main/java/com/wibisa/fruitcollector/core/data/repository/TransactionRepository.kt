package com.wibisa.fruitcollector.core.data.repository

import com.wibisa.fruitcollector.core.data.remote.network.ApiServices
import com.wibisa.fruitcollector.core.data.remote.response.CustomerTransaction
import com.wibisa.fruitcollector.core.domain.model.Commodity
import com.wibisa.fruitcollector.core.domain.model.InputAddCustomerTransaction
import com.wibisa.fruitcollector.core.domain.model.InputAddFarmerTransaction
import com.wibisa.fruitcollector.core.domain.model.TransactionFarmerCommodity
import com.wibisa.fruitcollector.core.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    suspend fun getListFarmerTransaction(token: String): Flow<ApiResult<List<TransactionFarmerCommodity>>> =
        flow {
            emit(ApiResult.Loading)
            try {
                val response = api.getListFarmerTransaction(token = token.tokenFormat())
                when (response.meta.status) {
                    API_RESPONSE_SUCCESS -> {
                        val commodities =
                            DataMapper.mapFarmerTransactionNetworkToDomain(response.data.farmerTransaction)
                        emit(ApiResult.Success(commodities))
                    }
                    API_RESPONSE_FAILED -> {
                        emit(ApiResult.Error(response.meta.message))
                    }
                    else -> {
                        emit(ApiResult.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResult.Error(e.message.toString()))
            }
        }

    suspend fun createCustomerTransaction(
        token: String,
        inputAddCustomerTransaction: InputAddCustomerTransaction
    ): Flow<ApiResult<String>> = flow {
        emit(ApiResult.Loading)
        try {
            val response = api.createCustomerTransaction(
                token = token.tokenFormat(),
                farmerTransactionId = inputAddCustomerTransaction.farmerTransactionId,
                quantity = inputAddCustomerTransaction.quantity,
                price = inputAddCustomerTransaction.price,
                buyerName = inputAddCustomerTransaction.buyerName,
                phone = inputAddCustomerTransaction.phone,
                address = inputAddCustomerTransaction.address,
                shippingDate = inputAddCustomerTransaction.shippingDate,
                shippingPrice = inputAddCustomerTransaction.shippingPrice,
                totalPrice = inputAddCustomerTransaction.totalPrice
            )
            when (response.meta.status) {
                API_RESPONSE_SUCCESS -> {
                    emit(ApiResult.Success(response.meta.message))
                }
                API_RESPONSE_FAILED -> {
                    emit(ApiResult.Error(response.meta.message))
                }
                else -> {
                    emit(ApiResult.Empty)
                }
            }
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message.toString()))
        }
    }

    suspend fun getListCustomerTransaction(token: String): Flow<ApiResult<List<CustomerTransaction>>> =
        flow {
            emit(ApiResult.Loading)
            try {
                val response = api.getListCustomerTransaction(token = token.tokenFormat())
                when (response.meta.status) {
                    API_RESPONSE_SUCCESS -> {
                        emit(ApiResult.Success(response.data.customerTransaction))
                    }
                    API_RESPONSE_FAILED -> {
                        emit(ApiResult.Error(response.meta.message))
                    }
                    else -> {
                        emit(ApiResult.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResult.Error(e.message.toString()))
            }
        }
}
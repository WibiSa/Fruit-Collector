package com.wibisa.fruitcollector.core.data.repository

import com.wibisa.fruitcollector.core.data.remote.network.ApiServices
import com.wibisa.fruitcollector.core.domain.model.Commodity
import com.wibisa.fruitcollector.core.domain.model.Fruit
import com.wibisa.fruitcollector.core.domain.model.InputAddCommodity
import com.wibisa.fruitcollector.core.domain.model.InputEditCommodity
import com.wibisa.fruitcollector.core.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommodityRepository @Inject constructor(private val api: ApiServices) {

    suspend fun getFruits(token: String): ApiResult<List<Fruit>> {
        try {
            val response = api.getFruits(token = token.tokenFormat())
            return when (response.meta.status) {
                API_RESPONSE_SUCCESS -> {
                    val fruit = DataMapper.mapFruitsNetworkToDomain(response.data.fruits)
                    ApiResult.Success(fruit)
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

    suspend fun addCommodity(
        token: String,
        inputAddCommodity: InputAddCommodity
    ): ApiResult<String> {
        try {
            val response = api.addCommodity(
                token = token.tokenFormat(),
                idFarmer = inputAddCommodity.idFarmer,
                idFruit = inputAddCommodity.idFruit
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

    suspend fun getCommodities(token: String): ApiResult<List<Commodity>> {
        try {
            val response = api.getCommodities(token = token.tokenFormat())
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

    suspend fun editCommodity(
        token: String,
        inputEditCommodity: InputEditCommodity
    ): ApiResult<String> {
        try {
            val response = api.editCommodity(
                token = token.tokenFormat(),
                idCommodity = inputEditCommodity.idCommodity,
                blossomsTreeDate = inputEditCommodity.blossomsTreeDate,
                harvestingDate = inputEditCommodity.harvestingDate,
                fruitGrade = inputEditCommodity.fruitGrade,
                stock = inputEditCommodity.stock
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

    suspend fun verifyCommodity(
        token: String,
        idCommodity: String
    ): ApiResult<String> {
        try {
            val response = api.verifyCommodity(
                token = token.tokenFormat(),
                idCommodity = idCommodity
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

    suspend fun deleteCommodity(
        token: String,
        idCommodity: String
    ): ApiResult<String> {
        try {
            val response = api.deleteCommodity(
                token = token.tokenFormat(),
                idCommodity = idCommodity
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
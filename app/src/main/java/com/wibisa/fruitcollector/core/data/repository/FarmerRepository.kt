package com.wibisa.fruitcollector.core.data.repository

import com.wibisa.fruitcollector.core.data.remote.network.ApiServices
import com.wibisa.fruitcollector.core.data.remote.response.asDomainModel
import com.wibisa.fruitcollector.core.domain.model.Farmer
import com.wibisa.fruitcollector.core.domain.model.InputAddFarmer
import com.wibisa.fruitcollector.core.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FarmerRepository @Inject constructor(private val api: ApiServices) {

    suspend fun addFarmer(token: String, inputAddFarmer: InputAddFarmer): ApiResult<String> {
        try {
            val response = api.addFarmer(
                token = token.tokenFormat(),
                name = inputAddFarmer.name,
                landLocation = inputAddFarmer.landLocation,
                numberOfTree = inputAddFarmer.numberOfTree,
                estimationProduction = inputAddFarmer.estimationProduction,
                landSize = inputAddFarmer.landSize
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

    suspend fun getFarmers(token: String): ApiResult<List<Farmer>> {
        try {
            val response = api.getFarmers(token = token.tokenFormat())
            return when (response.meta.status) {
                API_RESPONSE_SUCCESS -> {
                    val farmers = DataMapper.mapFarmersNetworkToDomain(response.data.farmer)
                    ApiResult.Success(farmers)
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

    suspend fun editFarmer(
        token: String,
        idFarmer: String,
        inputAddFarmer: InputAddFarmer
    ): ApiResult<Farmer> {
        try {
            val response = api.editFarmer(
                token = token.tokenFormat(),
                idFarmer = idFarmer,
                name = inputAddFarmer.name,
                landLocation = inputAddFarmer.landLocation,
                numberOfTree = inputAddFarmer.numberOfTree,
                estimationProduction = inputAddFarmer.estimationProduction,
                landSize = inputAddFarmer.landSize
            )
            return when (response.meta.status) {
                API_RESPONSE_SUCCESS -> {
                    ApiResult.Success(response.data.asDomainModel())
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

    suspend fun deleteFarmer(token: String, idFarmer: String): ApiResult<String> {
        try {
            val response = api.deleteFarmer(token.tokenFormat(), idFarmer)
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
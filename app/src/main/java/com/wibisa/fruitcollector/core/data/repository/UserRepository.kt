package com.wibisa.fruitcollector.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.wibisa.fruitcollector.core.data.remote.network.ApiServices
import com.wibisa.fruitcollector.core.data.remote.response.asDomainModel
import com.wibisa.fruitcollector.core.domain.model.*
import com.wibisa.fruitcollector.core.util.API_RESPONSE_FAILED
import com.wibisa.fruitcollector.core.util.API_RESPONSE_SUCCESS
import com.wibisa.fruitcollector.core.util.ApiResult
import com.wibisa.fruitcollector.core.util.tokenFormat
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val api: ApiServices,
    private val dataStore: DataStore<Preferences>
) {

    suspend fun register(inputRegister: InputRegister): ApiResult<Register> {
        try {
            val response = api.register(
                name = inputRegister.name,
                phone = inputRegister.phone,
                email = inputRegister.email,
                password = inputRegister.password,
                passwordConfirm = inputRegister.passwordConfirm
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

    suspend fun login(inputLogin: InputLogin): ApiResult<Login> {
        try {
            val response = api.login(email = inputLogin.email, password = inputLogin.password)
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

    suspend fun logout(token: String): ApiResult<Logout> {
        try {
            val response = api.logout(token.tokenFormat())
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

    suspend fun saveUserPreferencesToDataStore(userPreferences: UserPreferences) {
        dataStore.edit {
            it[USER_ID] = userPreferences.userId
            it[TOKEN] = userPreferences.token
            it[NAME] = userPreferences.name
        }
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val customerId = preferences[USER_ID] ?: EMPTY_STRING
        val token = preferences[TOKEN] ?: EMPTY_STRING
        val name = preferences[NAME] ?: EMPTY_STRING

        return UserPreferences(customerId, token, name)
    }

    suspend fun fetchUserPreferences() = mapUserPreferences(dataStore.data.first().toPreferences())

    suspend fun clearUserPreferencesFromDataStore() {
        dataStore.edit { it.clear() }
    }

    companion object {
        private val USER_ID = stringPreferencesKey("user_id")
        private val TOKEN = stringPreferencesKey("token")
        private val NAME = stringPreferencesKey("name")
        private const val EMPTY_STRING = ""
    }
}
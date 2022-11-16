package com.wibisa.fruitcollector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wibisa.fruitcollector.core.data.repository.CommodityRepository
import com.wibisa.fruitcollector.core.data.repository.FarmerRepository
import com.wibisa.fruitcollector.core.data.repository.UserRepository
import com.wibisa.fruitcollector.core.domain.model.*
import com.wibisa.fruitcollector.core.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordCommodityViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val farmerRepository: FarmerRepository,
    private val commodityRepository: CommodityRepository
) : ViewModel() {

    val farmerId = MutableLiveData<String?>(null)

    val fruitId = MutableLiveData<String?>(null)

    private val _userPreferences = MutableLiveData<UserPreferences>()
    val userPreferences: LiveData<UserPreferences>
        get() = _userPreferences

    private val _farmersUiState = MutableStateFlow<ApiResult<List<Farmer>>>(ApiResult.Empty)
    val farmersUiState: StateFlow<ApiResult<List<Farmer>>> = _farmersUiState.asStateFlow()

    private val _fruitsUiState = MutableStateFlow<ApiResult<List<Fruit>>>(ApiResult.Empty)
    val fruitsUiState: StateFlow<ApiResult<List<Fruit>>> = _fruitsUiState.asStateFlow()

    private val _addCommodityUiState = MutableStateFlow<ApiResult<String>>(ApiResult.Empty)
    val addCommodityUiState: StateFlow<ApiResult<String>> = _addCommodityUiState.asStateFlow()

    init {
        viewModelScope.launch {
            _userPreferences.value = userRepository.fetchUserPreferences()
        }
    }

    fun getFarmers(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _farmersUiState.value = ApiResult.Loading
            val response = farmerRepository.getFarmers(token)
            _farmersUiState.value = response
        }
    }

    fun getFarmersCompleted() {
        _farmersUiState.value = ApiResult.Empty
    }

    fun getFruits(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _fruitsUiState.value = ApiResult.Loading
            val response = commodityRepository.getFruits(token)
            _fruitsUiState.value = response
        }
    }

    fun getFruitsCompleted() {
        _fruitsUiState.value = ApiResult.Empty
    }

    fun addCommodity(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val input = InputAddCommodity(
                idFarmer = farmerId.value!!,
                idFruit = fruitId.value!!
            )

            _addCommodityUiState.value = ApiResult.Loading
            val response = commodityRepository.addCommodity(token, input)
            _addCommodityUiState.value = response
        }
    }

    fun addCommodityCompleted() {
        _addCommodityUiState.value = ApiResult.Empty
    }
}
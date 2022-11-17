package com.wibisa.fruitcollector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wibisa.fruitcollector.core.data.repository.CommodityRepository
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
class CommodityDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val commodityRepository: CommodityRepository
) : ViewModel() {

    private val _userPreferences = MutableLiveData<UserPreferences>()
    val userPreferences: LiveData<UserPreferences>
        get() = _userPreferences

    private val _editCommodityUiState = MutableStateFlow<ApiResult<String>>(ApiResult.Empty)
    val editCommodityUiState: StateFlow<ApiResult<String>> = _editCommodityUiState.asStateFlow()

    private val _verifyCommodityUiState = MutableStateFlow<ApiResult<String>>(ApiResult.Empty)
    val verifyCommodityUiState: StateFlow<ApiResult<String>> = _verifyCommodityUiState.asStateFlow()

    private val _deleteCommodityUiState = MutableStateFlow<ApiResult<String>>(ApiResult.Empty)
    val deleteCommodityUiState: StateFlow<ApiResult<String>> = _deleteCommodityUiState.asStateFlow()

    init {
        viewModelScope.launch {
            _userPreferences.value = userRepository.fetchUserPreferences()
        }
    }

    fun editCommodity(token: String, inputEditCommodity: InputEditCommodity) {
        viewModelScope.launch(Dispatchers.IO) {
            _editCommodityUiState.value = ApiResult.Loading
            val response = commodityRepository.editCommodity(token, inputEditCommodity)
            _editCommodityUiState.value = response
        }
    }

    fun editCommodityCompleted() {
        _editCommodityUiState.value = ApiResult.Empty
    }

    fun verifyCommodity(token: String, idCommodity: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _verifyCommodityUiState.value = ApiResult.Loading
            val response = commodityRepository.verifyCommodity(token, idCommodity)
            _verifyCommodityUiState.value = response
        }
    }

    fun verifyCommodityCompleted() {
        _verifyCommodityUiState.value = ApiResult.Empty
    }

    fun deleteCommodity(token: String, idCommodity: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _deleteCommodityUiState.value = ApiResult.Loading
            val response = commodityRepository.deleteCommodity(token, idCommodity)
            _deleteCommodityUiState.value = response
        }
    }

    fun deleteCommodityCompleted() {
        _deleteCommodityUiState.value = ApiResult.Empty
    }
}
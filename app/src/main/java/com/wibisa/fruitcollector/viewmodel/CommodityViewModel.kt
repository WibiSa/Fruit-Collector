package com.wibisa.fruitcollector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wibisa.fruitcollector.core.data.remote.response.Commodity
import com.wibisa.fruitcollector.core.data.repository.CommodityRepository
import com.wibisa.fruitcollector.core.data.repository.UserRepository
import com.wibisa.fruitcollector.core.domain.model.UserPreferences
import com.wibisa.fruitcollector.core.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommodityViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val commodityRepository: CommodityRepository
) : ViewModel() {

    private val _userPreferences = MutableLiveData<UserPreferences>()
    val userPreferences: LiveData<UserPreferences>
        get() = _userPreferences

    private val _commoditiesUiState =
        MutableStateFlow<ApiResult<List<Commodity>>>(ApiResult.Empty)
    val commoditiesUiState: StateFlow<ApiResult<List<Commodity>>> =
        _commoditiesUiState.asStateFlow()

    init {
        viewModelScope.launch {
            _userPreferences.value = userRepository.fetchUserPreferences()
        }
    }

    fun getCommodity(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _commoditiesUiState.value = ApiResult.Loading
            val response = commodityRepository.getCommodities(token)
            _commoditiesUiState.value = response
        }
    }

    fun getCommodityCompleted() {
        _commoditiesUiState.value = ApiResult.Empty
    }
}
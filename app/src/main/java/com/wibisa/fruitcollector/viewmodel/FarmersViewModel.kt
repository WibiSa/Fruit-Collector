package com.wibisa.fruitcollector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.wibisa.fruitcollector.core.data.repository.FarmerRepository
import com.wibisa.fruitcollector.core.data.repository.UserRepository
import com.wibisa.fruitcollector.core.domain.model.Farmer
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
class FarmersViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val farmerRepository: FarmerRepository
) : ViewModel() {

    private val _userPreferences = MutableLiveData<UserPreferences>()
    val userPreferences: LiveData<UserPreferences>
        get() = _userPreferences

    private val _farmersUiState = MutableStateFlow<ApiResult<List<Farmer>>>(ApiResult.Empty)
    val farmersUiState: StateFlow<ApiResult<List<Farmer>>> = _farmersUiState.asStateFlow()

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
}
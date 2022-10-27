package com.wibisa.fruitcollector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.wibisa.fruitcollector.core.data.repository.FarmerRepository
import com.wibisa.fruitcollector.core.data.repository.UserRepository
import com.wibisa.fruitcollector.core.domain.model.InputAddFarmer
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
class AddFarmerViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val farmerRepository: FarmerRepository
) : ViewModel() {

    private val _userPreferences = MutableLiveData<UserPreferences>()
    val userPreferences: LiveData<UserPreferences>
        get() = _userPreferences

    private val _addFarmerUiState = MutableStateFlow<ApiResult<String>>(ApiResult.Empty)
    val addFarmerUiState: StateFlow<ApiResult<String>> = _addFarmerUiState.asStateFlow()

    init {
        viewModelScope.launch {
            _userPreferences.value = userRepository.fetchUserPreferences()
        }
    }

    fun addFarmer(token: String, inputAddFarmer: InputAddFarmer) {
        viewModelScope.launch(Dispatchers.IO) {
            _addFarmerUiState.value = ApiResult.Loading
            val response = farmerRepository.addFarmer(token, inputAddFarmer)
            _addFarmerUiState.value = response
        }
    }

    fun addFarmerCompleted() {
        _addFarmerUiState.value = ApiResult.Empty
    }
}
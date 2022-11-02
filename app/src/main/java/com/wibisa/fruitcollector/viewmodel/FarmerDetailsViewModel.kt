package com.wibisa.fruitcollector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wibisa.fruitcollector.core.data.repository.FarmerRepository
import com.wibisa.fruitcollector.core.data.repository.UserRepository
import com.wibisa.fruitcollector.core.domain.model.Farmer
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
class FarmerDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val farmerRepository: FarmerRepository
) : ViewModel() {

    private val _userPreferences = MutableLiveData<UserPreferences>()
    val userPreferences: LiveData<UserPreferences>
        get() = _userPreferences

    private val _editFarmerUiState = MutableStateFlow<ApiResult<Farmer>>(ApiResult.Empty)
    val editFarmerUiState: StateFlow<ApiResult<Farmer>> = _editFarmerUiState.asStateFlow()

    private val _deleteFarmerUiState = MutableStateFlow<ApiResult<String>>(ApiResult.Empty)
    val deleteFarmerUiState: StateFlow<ApiResult<String>> = _deleteFarmerUiState.asStateFlow()

    init {
        viewModelScope.launch {
            _userPreferences.value = userRepository.fetchUserPreferences()
        }
    }

    fun editFarmer(token: String, idFarmer: String, inputAddFarmer: InputAddFarmer) {
        viewModelScope.launch(Dispatchers.IO) {
            _editFarmerUiState.value = ApiResult.Loading
            val response = farmerRepository.editFarmer(token, idFarmer, inputAddFarmer)
            _editFarmerUiState.value = response
        }
    }

    fun editFarmerCompleted() {
        _editFarmerUiState.value = ApiResult.Empty
    }

    fun deleteFarmer(token: String, idFarmer: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _deleteFarmerUiState.value = ApiResult.Loading
            val response = farmerRepository.deleteFarmer(token, idFarmer)
            _deleteFarmerUiState.value = response
        }
    }

    fun deleteFarmerCompleted() {
        _deleteFarmerUiState.value = ApiResult.Empty
    }
}
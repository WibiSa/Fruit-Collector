package com.wibisa.fruitcollector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wibisa.fruitcollector.core.data.repository.UserRepository
import com.wibisa.fruitcollector.core.domain.model.Logout
import com.wibisa.fruitcollector.core.domain.model.UserPreferences
import com.wibisa.fruitcollector.core.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: sementara fungsi logout disini, nanti dipindah!

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _userPreferences = MutableLiveData<UserPreferences>()
    val userPreferences: LiveData<UserPreferences>
        get() = _userPreferences

    private val _logoutUiState = MutableStateFlow<ApiResult<Logout>>(ApiResult.Empty)
    val logoutUiState: StateFlow<ApiResult<Logout>> = _logoutUiState.asStateFlow()

    init {
        viewModelScope.launch {
            _userPreferences.value = userRepository.fetchUserPreferences()
        }
    }

    fun logout(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _logoutUiState.value = ApiResult.Loading
            val response = userRepository.logout(token)
            _logoutUiState.value = response
        }
    }

    fun logoutCompleted() {
        _logoutUiState.value = ApiResult.Empty
    }

    fun clearUserPreferences() {
        viewModelScope.launch(Dispatchers.IO) { userRepository.clearUserPreferencesFromDataStore() }
    }
}
package com.wibisa.fruitcollector.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wibisa.fruitcollector.core.data.repository.UserRepository
import com.wibisa.fruitcollector.core.domain.model.InputLogin
import com.wibisa.fruitcollector.core.domain.model.Login
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
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _loginUiState = MutableStateFlow<ApiResult<Login>>(ApiResult.Empty)
    val loginUiState: StateFlow<ApiResult<Login>> = _loginUiState.asStateFlow()

    fun login(inputLogin: InputLogin) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginUiState.value = ApiResult.Loading
            val response = userRepository.login(inputLogin)
            _loginUiState.value = response
        }
    }

    fun loginCompleted() {
        _loginUiState.value = ApiResult.Empty
    }

    fun saveUserPreferences(userPreferences: UserPreferences) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.saveUserPreferencesToDataStore(userPreferences)
        }
    }
}
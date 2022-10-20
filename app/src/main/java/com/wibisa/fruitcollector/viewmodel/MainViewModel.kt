package com.wibisa.fruitcollector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wibisa.fruitcollector.core.data.repository.UserRepository
import com.wibisa.fruitcollector.core.domain.model.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _splashIsLoading = MutableStateFlow(true)
    val splashIsLoading = _splashIsLoading.asStateFlow()

    private val _isFirstTimeLaunchApp = MutableStateFlow(true)
    val isFirstTimeLaunchApp = _isFirstTimeLaunchApp.asStateFlow()

    private val _userPreferences = MutableLiveData<UserPreferences>()
    val userPreferences: LiveData<UserPreferences>
        get() = _userPreferences

    init {
        viewModelScope.launch {
            _userPreferences.value = userRepository.fetchUserPreferences()
            _splashIsLoading.value = false
        }
    }

    fun appWasLaunch() {
        _isFirstTimeLaunchApp.value = !isFirstTimeLaunchApp.value
    }
}
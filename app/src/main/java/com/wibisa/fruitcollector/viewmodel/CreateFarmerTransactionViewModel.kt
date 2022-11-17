package com.wibisa.fruitcollector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wibisa.fruitcollector.core.data.repository.TransactionRepository
import com.wibisa.fruitcollector.core.data.repository.UserRepository
import com.wibisa.fruitcollector.core.domain.model.Commodity
import com.wibisa.fruitcollector.core.domain.model.InputAddFarmerTransaction
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
class CreateFarmerTransactionViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val selectedCommodity = MutableLiveData<Commodity>(null)

    val totalPrice = MutableLiveData(0)

    private val _userPreferences = MutableLiveData<UserPreferences>()
    val userPreferences: LiveData<UserPreferences>
        get() = _userPreferences

    private val _commoditiesUiState =
        MutableStateFlow<ApiResult<List<Commodity>>>(ApiResult.Empty)
    val commoditiesUiState: StateFlow<ApiResult<List<Commodity>>> =
        _commoditiesUiState.asStateFlow()

    private val _createFarmerTransactionUiState =
        MutableStateFlow<ApiResult<String>>(ApiResult.Empty)
    val createFarmerTransactionUiState: StateFlow<ApiResult<String>> =
        _createFarmerTransactionUiState.asStateFlow()

    init {
        viewModelScope.launch {
            _userPreferences.value = userRepository.fetchUserPreferences()
        }
    }

    fun getCommodity(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _commoditiesUiState.value = ApiResult.Loading
            val response = transactionRepository.getVerifiedCommodities(token)
            _commoditiesUiState.value = response
        }
    }

    fun getCommodityCompleted() {
        _commoditiesUiState.value = ApiResult.Empty
    }

    fun createFarmerTransaction(token: String, quantity: Int, price: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val input = InputAddFarmerTransaction(
                idCommodity = selectedCommodity.value!!.id,
                quantity = quantity,
                price = price,
                totalPrice = totalPrice.value!!
            )
            _createFarmerTransactionUiState.value = ApiResult.Loading
            val response = transactionRepository.createFarmerTransaction(token, input)
            _createFarmerTransactionUiState.value = response
        }
    }

    fun createFarmerTransactionCompleted() {
        _createFarmerTransactionUiState.value = ApiResult.Empty
    }
}
package com.wibisa.fruitcollector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wibisa.fruitcollector.core.data.repository.TransactionRepository
import com.wibisa.fruitcollector.core.data.repository.UserRepository
import com.wibisa.fruitcollector.core.domain.model.InputAddCustomerTransaction
import com.wibisa.fruitcollector.core.domain.model.TransactionFarmerCommodity
import com.wibisa.fruitcollector.core.domain.model.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCustomerTransactionViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val selectedFarmerTransaction = MutableLiveData<TransactionFarmerCommodity>(null)

    private val _userPreferences = MutableLiveData<UserPreferences>()
    val userPreferences: LiveData<UserPreferences>
        get() = _userPreferences

    init {
        viewModelScope.launch {
            _userPreferences.value = userRepository.fetchUserPreferences()
        }
    }

    suspend fun getListFarmerTransaction(token: String) =
        transactionRepository.getListFarmerTransaction(token)

    suspend fun createCustomerTransaction(
        token: String,
        inputAddCustomerTransaction: InputAddCustomerTransaction
    ) =
        transactionRepository.createCustomerTransaction(token, inputAddCustomerTransaction)
}
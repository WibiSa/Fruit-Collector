package com.wibisa.fruitcollector.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListCustomerTransactionNetwork(
    val meta: Meta,
    val `data`: CustomerTransactionData
)

data class CustomerTransactionData(
    @SerializedName("customer_transaction")
    val customerTransaction: List<CustomerTransaction>
)

data class CustomerTransaction(
    val id: String,
    @SerializedName("customer_id")
    val customerId: String,
    @SerializedName("farmer_transaction_id")
    val farmerTransactionId: String,
    @SerializedName("shiping_date")
    val shipingDate: String,
    val weight: Int,
    @SerializedName("total_payment")
    val totalPayment: Int,
    @SerializedName("struck_link")
    val struckLink: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val address: String,
    @SerializedName("receiver_name")
    val receiverName: String,
    @SerializedName("shiping_payment")
    val shipingPayment: Int,
    val price: Int,
    @SerializedName("farmer_transaction")
    val farmerTransaction: FarmerTransaction,
    val customer: Customer
)

data class Customer(
    val id: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
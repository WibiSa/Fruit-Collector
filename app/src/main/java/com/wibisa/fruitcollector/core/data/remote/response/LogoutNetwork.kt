package com.wibisa.fruitcollector.core.data.remote.response

import com.wibisa.fruitcollector.core.domain.model.Logout

data class LogoutNetwork(
    val meta: Meta,
    val `data`: LogoutData
)

data class LogoutData(
    val message: String
)

fun LogoutData.asDomainModel(): Logout =
    Logout(message = message)
package com.wibisa.fruitcollector.core.data.remote.response

import com.wibisa.fruitcollector.core.domain.model.Logout

data class LogoutNetwork(
    val meta: Meta,
    val `data`: Any?
)
//
//data class LogoutData(
//    val message: String
//)

fun LogoutNetwork.asDomainModel(): Logout =
    Logout(message = meta.message)
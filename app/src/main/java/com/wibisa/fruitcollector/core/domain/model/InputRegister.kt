package com.wibisa.fruitcollector.core.domain.model

data class InputRegister(
    val name: String,
    val phone: String,
    val email: String,
    val password: String,
    val passwordConfirm: String
)

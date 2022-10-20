package com.wibisa.fruitcollector.core.domain.model

data class UserPreferences(
    val userId: String,
    val token: String,
    val name: String
)
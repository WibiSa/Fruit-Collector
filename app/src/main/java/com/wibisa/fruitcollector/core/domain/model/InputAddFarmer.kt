package com.wibisa.fruitcollector.core.domain.model

data class InputAddFarmer(
    val name: String,
    val landLocation: String,
    val numberOfTree: Int,
    val estimationProduction: Int,
    val landSize: Float
)

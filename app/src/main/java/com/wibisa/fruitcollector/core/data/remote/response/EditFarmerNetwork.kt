package com.wibisa.fruitcollector.core.data.remote.response

data class EditFarmerNetwork(
    val meta: Meta,
    val `data`: FarmerData
)

data class FarmerData(
    val farmer: Farmer
)

fun FarmerData.asDomainModel(): com.wibisa.fruitcollector.core.domain.model.Farmer =
    com.wibisa.fruitcollector.core.domain.model.Farmer(
        id = farmer.id,
        name = farmer.name,
        landLocation = farmer.landLocation,
        landSize = farmer.landSize,
        estimationProduction = farmer.estimationProduction,
        numberTree = farmer.numberTree
    )

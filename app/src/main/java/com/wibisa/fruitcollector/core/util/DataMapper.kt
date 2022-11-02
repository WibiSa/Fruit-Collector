package com.wibisa.fruitcollector.core.util

import com.wibisa.fruitcollector.core.data.remote.response.Farmer
import com.wibisa.fruitcollector.core.data.remote.response.Fruit

object DataMapper {

    fun mapFarmersNetworkToDomain(list: List<Farmer>): List<com.wibisa.fruitcollector.core.domain.model.Farmer> =
        list.map {
            com.wibisa.fruitcollector.core.domain.model.Farmer(
                id = it.id,
                name = it.name,
                landLocation = it.landLocation,
                numberTree = it.numberTree,
                estimationProduction = it.estimationProduction,
                landSize = it.landSize
            )
        }

    fun mapFruitsNetworkToDomain(list: List<Fruit>): List<com.wibisa.fruitcollector.core.domain.model.Fruit> =
        list.map {
            com.wibisa.fruitcollector.core.domain.model.Fruit(
                id = it.id,
                name = it.name
            )
        }
}
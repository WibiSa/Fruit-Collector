package com.wibisa.fruitcollector.core.util

import com.wibisa.fruitcollector.core.data.remote.response.Commodity
import com.wibisa.fruitcollector.core.data.remote.response.Farmer
import com.wibisa.fruitcollector.core.data.remote.response.FarmerTransaction
import com.wibisa.fruitcollector.core.data.remote.response.Fruit
import com.wibisa.fruitcollector.core.domain.model.TransactionFarmerCommodity

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

    fun mapCommodityNetworkToDomain(list: List<Commodity>): List<com.wibisa.fruitcollector.core.domain.model.Commodity> =
        list.map {
            com.wibisa.fruitcollector.core.domain.model.Commodity(
                id = it.id,
                commodityName = it.fruit.name,
                farmerName = it.farmer.name,
                blossomsTreeDate = it.blossomsTreeDate,
                grade = it.fruitGrade,
                harvestDate = it.harvestingDate,
                stock = it.weight,
                isValid = it.verified
            )
        }

    fun mapFarmerTransactionNetworkToDomain(list: List<FarmerTransaction>): List<TransactionFarmerCommodity> =
        list.map {
            TransactionFarmerCommodity(
                id = it.id,
                commodityName = it.fruitCommodity.fruit.name,
                farmerName = it.fruitCommodity.farmer.name,
                grade = it.fruitCommodity.fruitGrade,
                harvestDate = it.fruitCommodity.harvestingDate,
                stock = it.weight,
                pricePerKg = it.priceKg
            )
        }
}
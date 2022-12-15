package com.wibisa.fruitcollector.core.util

import com.wibisa.fruitcollector.core.data.remote.response.*
import com.wibisa.fruitcollector.core.domain.model.Farmer
import com.wibisa.fruitcollector.core.domain.model.Fruit
import com.wibisa.fruitcollector.core.domain.model.TransactionFarmerCommodity

object DataMapper {

    fun mapFarmersNetworkToDomain(list: List<FarmersDataX>): List<Farmer> =
        list.map {
            Farmer(
                id = it.id,
                name = it.name,
                landLocation = it.landLocation,
                numberTree = it.numberTree,
                estimationProduction = it.estimationProduction,
                landSize = it.landSize
            )
        }

    fun mapFruitsNetworkToDomain(list: List<FruitData>): List<Fruit> =
        list.map {
            Fruit(
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

    fun mapFarmerTransactionNetworkToDomain(list: List<FarmerTransactionData>): List<TransactionFarmerCommodity> =
        list.map {
            TransactionFarmerCommodity(
                id = it.id,
                commodityName = it.fruitCommodity.fruit.name,
                farmerName = it.fruitCommodity.farmer.name,
                grade = it.fruitCommodity.fruitGrade,
                blossomsTreeDate = it.fruitCommodity.blossomsTreeDate,
                harvestDate = it.fruitCommodity.harvestingDate,
                stock = it.weight,
                pricePerKg = it.priceKg,
                priceTotal = it.payment,
                createdAt = it.createdAt
            )
        }
}
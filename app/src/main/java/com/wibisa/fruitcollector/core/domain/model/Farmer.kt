package com.wibisa.fruitcollector.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Farmer(
    val id: String,
    val name: String,
    val landLocation: String,
    val numberTree: Int,
    val estimationProduction: Int,
    val landSize: Double
) : Parcelable

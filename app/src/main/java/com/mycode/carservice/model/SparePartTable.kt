package com.mycode.carservice.model

import java.io.Serializable

data class SparePartTable (
    val Manufacture : String,
    val Price : String,
    val carModel : String,
    val city : String,
    val daysToDeliver : String,
    val img : String,
    val partName : String,
    val type : String
        ): Serializable
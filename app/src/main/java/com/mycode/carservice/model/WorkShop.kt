package com.mycode.carservice.model

import java.io.Serializable

data class WorkShop(
    val workshopId:String,
    val city : String,
    val closingHours:String,
    val detail:String,
    val img : String,
    val lastWorkingDay:String,
    val name : String,
    val openingHours: String,
    var rating: ArrayList<Int>,
    val startWorkingDay:String,
    val phoneNumber: String,
    val workshopEmail:String
):Serializable

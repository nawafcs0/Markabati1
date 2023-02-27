package com.mycode.carservice.model

import java.io.Serializable

data class TransporterTable(
    var transporterId:String,
    var name : String,
    var city : String,
    var price : String,
    var rate : ArrayList<Int>,
    var transporterPhoneNumber : String,
    var transporterEmail:String,
    ): Serializable
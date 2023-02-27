package com.mycode.carservice.model

import java.io.Serializable

data class SpareParts(
    val partId:String,
    val title:String,
    val price:String,
    val type:String ,
    val img:String,
    val city:String,
    val partName:String,
    val daysToDeliver:Long
    ):Serializable

data class SparePartsCustomerReq(
    val partId:String,
    val partName:String,
    val price:String,
    val type:String ,
    val img:String,
    val city:String,

    val requesterName:String,
    val requesterId:String,
    val requesterEmail:String,
    val daysToDeliver: String


):Serializable
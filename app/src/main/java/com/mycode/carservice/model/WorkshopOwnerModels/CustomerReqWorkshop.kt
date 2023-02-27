package com.mycode.carservice.model.WorkshopOwnerModels

import java.io.Serializable

data class CustomerReqWorkshop(var requestId:String, var customerName:String, var carName:String, var customerPhone:String,
        var description:String,var workshopEmail:String ,var workshopName:String):
    Serializable
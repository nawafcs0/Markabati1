package com.mycode.carservice.model.WorkshopOwnerModels

import java.io.Serializable

data class AcceptedReqWorkshop(var customerName:String,var carName:String,var customerPhone:String
        ,var status:String,var workshopEmail:String):
    Serializable
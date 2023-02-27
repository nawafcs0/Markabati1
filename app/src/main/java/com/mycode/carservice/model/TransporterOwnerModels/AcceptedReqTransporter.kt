package com.mycode.carservice.model.TransporterOwnerModels

import java.io.Serializable

data class AcceptedReqTransporter(var customerName:String, var carName:String, var customerPhone:String
                                  , var status:String, var transporterEmail:String
                                  ,var from:String,var to :String):
    Serializable
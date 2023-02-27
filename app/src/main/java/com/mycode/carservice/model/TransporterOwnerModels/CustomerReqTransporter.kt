package com.mycode.carservice.model.TransporterOwnerModels

import java.io.Serializable

data class CustomerReqTransporter(var requestId:String, var customerName:String, var carName:String, var customerPhone:String,
                                   var from:String ,var to :String,var transporterEmail:String, var transporterId:String
                                   ): Serializable
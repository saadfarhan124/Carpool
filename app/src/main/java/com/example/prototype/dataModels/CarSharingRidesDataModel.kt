package com.example.prototype.dataModels

import java.io.Serializable

class CarSharingRidesDataModel : Serializable{
    var date:String? = null
    var pickUpAddress:String? = null
    var dropOffAddress:String? = null
    var pickUpTime:String? = null
    var dropOffTime:String? = null
    var status:String? = null
    var requestID:String? = null
    var userID:String? = null

    constructor(date:String,pickUpAddress:String ,dropOffAddress:String
                ,pickUpTime:String ,dropOffTime:String
                ,status:String ,requestID:String, userID:String){
        this.date = date
        this.pickUpAddress = pickUpAddress
        this.dropOffAddress = dropOffAddress
        this.pickUpTime = pickUpTime
        this.dropOffTime = dropOffTime
        this.status = status
        this.requestID = requestID
        this.userID = userID

    }
}
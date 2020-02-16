package com.example.prototype.dataModels

import java.io.Serializable

class CarSharingRidesDataModel : Serializable{
    var date:String? = null
    var pickUpAddress:String? = null
    var dropOffAddress:String? = null
    var pickUpTime:String? = null
    var dropOffTime:String? = null
    var rideStatus:String? = null
    var userID:String? = null
    var bookingID: String? = null
    var routeID:String? = null

    constructor(date:String,pickUpAddress:String ,dropOffAddress:String
                ,pickUpTime:String ,dropOffTime:String
                ,rideStatus:String, userID:String){
        this.date = date
        this.pickUpAddress = pickUpAddress
        this.dropOffAddress = dropOffAddress
        this.pickUpTime = pickUpTime
        this.dropOffTime = dropOffTime
        this.rideStatus = rideStatus
        this.userID = userID

    }

    constructor()
}
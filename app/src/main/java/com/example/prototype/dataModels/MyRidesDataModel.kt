package com.example.prototype.dataModels

import java.io.Serializable

class MyRides : Serializable {

    var rideId:String? = null
    var bookingDate: String? = null
    var bookingId:Long = 0
    var totalFare:Long = 0
    var startingTime: String? = null
    var endTime: String? = null
    var pickUpPoint: String? = null
    var dropOffPoint: String? = null
    var rideStatus:Boolean = false
    var customerID:String? = null

    constructor() {}
    constructor(

        bookingDate: String?,
        bookingId:Long,
        totalFare:Long,
        startingTime:String,
        endTime:String,
        pickUpPoint: String?,
        dropOffPoint: String?,
        customerID:String?
    ) {

        this.bookingDate = bookingDate
        this.bookingId = bookingId
        this.totalFare = totalFare
        this.startingTime = startingTime
        this.endTime = endTime
        this.pickUpPoint = pickUpPoint
        this.dropOffPoint = dropOffPoint
        this.customerID = customerID

    }


}
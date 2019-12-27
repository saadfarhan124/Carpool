package com.example.prototype.dataModels

import java.io.Serializable

class MyRides : Serializable {

    var routeId:String? = null
    var bookingDate: String? = null
    var bookingId:Long = 0
    var totalFare:Long = 0
    var startingTime: String? = null
    var endTime: String? = null
    var pickUpPoint: String? = null
    var dropOffPoint: String? = null
    var rideStatus:String = ""
    var customerID:String? = null
    var rideId:String? = null

    constructor() {}
    constructor(
        bookingDate: String?,
        bookingId:Long,
        totalFare:Long,
        startingTime:String,
        endTime:String,
        pickUpPoint: String?,
        dropOffPoint: String?,
        customerID:String?,
        routeId:String?
    ) {

        this.bookingDate = bookingDate
        this.bookingId = bookingId
        this.totalFare = totalFare
        this.startingTime = startingTime
        this.endTime = endTime
        this.pickUpPoint = pickUpPoint
        this.dropOffPoint = dropOffPoint
        this.customerID = customerID
        this.routeId = routeId

    }

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["bookingDate"] = bookingDate!!
        result["bookingId"] = bookingId!!
        result["totalFare"] = totalFare!!
        result["startingTime"] = startingTime!!
        result["endTime"] = endTime!!
        result["pickUpPoint"] = pickUpPoint!!
        result["dropOffPoint"] = dropOffPoint!!
        result["customerID"] = customerID!!
        return result
    }
}
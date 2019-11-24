package com.example.prototype.dataModels

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

class Booking : Serializable {

    var startingTime: String? = null
    var startingPoint:String? = null
    var endingTime:String? = null
    var endingPoint:String? = null
    var documentId:String? = null
    var bookingId:Long = 0
    var numberOfSeats:Long = 0
    var bookingDate:String? = null
    var bookingMadeBy:String? = null
    var totalFare:Long = 0


    constructor() {}
    constructor(
        startingTime: String?,
        startingPoint: String?,
        endingTime: String?,
        endingPoint: String?,
        bookingId: Long,
        numberOfSeats: Long,
        bookingDate: String?,
        bookingMadeBy: String?,
        totalFare: Long
    ) {
        this.startingTime = startingTime
        this.startingPoint = startingPoint
        this.endingTime = endingTime
        this.endingPoint = endingPoint
        this.bookingId = bookingId
        this.numberOfSeats = numberOfSeats
        this.bookingDate = bookingDate
        this.bookingMadeBy = bookingMadeBy
        this.totalFare = totalFare
    }


}
package com.example.prototype.dataModels

import java.io.Serializable

class ReviewInformationDataModel : Serializable {
    var pickUpLat: Double? = null
    var pickUpLong: Double? = null
    var pickUpAddress: String = ""

    var dropOffLat: Double? = null
    var dropOffLong: Double? = null
    var dropOffAddress: String = ""

    constructor(pickUpLat:Double, pickUpLong:Double, pickUpAddress:String,
                dropOffLat:Double, dropOffLong:Double, dropOffAddress:String){
        this.pickUpLat = pickUpLat
        this.pickUpLong = pickUpLong
        this.pickUpAddress = pickUpAddress

        this.dropOffLat = dropOffLat
        this.dropOffLong = dropOffLong
        this.dropOffAddress = dropOffAddress
    }
}
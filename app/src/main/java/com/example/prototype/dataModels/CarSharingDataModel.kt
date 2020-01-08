package com.example.prototype.dataModels

import java.io.Serializable

class CarSharingDataModel:Serializable{
    var day:String? = null
    var pickUpTime:String? = null
    var dropOffTime:String? = null

    constructor(day:String, pickUpTime:String, dropOffTime:String){
        this.day = day
        this.pickUpTime = pickUpTime
        this.dropOffTime = dropOffTime
    }
}
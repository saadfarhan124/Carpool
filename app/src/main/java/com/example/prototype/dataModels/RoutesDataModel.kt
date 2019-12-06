package com.example.prototype.dataModels

import java.io.Serializable

class Routes : Serializable {

    var startingTime: String? = null
    var startingPoint: String? = null
    var endingTime: String? = null
    var endingPoint: String? = null
    var id: String? = null
    var seatsRemaining: Long = 0
    var distanceFromPickUp: Float = 0f
    var distanceFromDropOff: Float = 0f

    constructor()

    constructor(
        starting_time: String,
        starting_point: String,
        ending_time: String,
        ending_point: String,
        seatsRemaining: Long,
        distanceFromPickUp: Float,
        distanceFromDropOff: Float
    ) {
        this.startingTime = starting_time
        this.startingPoint = starting_point
        this.endingTime = ending_time
        this.endingPoint = ending_point
        this.seatsRemaining = seatsRemaining
        this.distanceFromPickUp = distanceFromPickUp
        this.distanceFromDropOff = distanceFromDropOff
    }

    fun toMap(): Map<String, Any> {
        val result = HashMap<String, Any>()
        result["startingTime"] = startingTime!!
        result["startingPoint"] = startingPoint!!
        result["endingTime"] = endingTime!!
        result["endingPoint"] = endingPoint!!
        result["seatsRemaining"] = seatsRemaining!!
        result["distanceFromPickUp"] = distanceFromPickUp!!
        result["distanceFromDropOff"] = distanceFromDropOff!!
        return result
    }

}
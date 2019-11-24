package com.example.prototype.dataModels

import java.io.Serializable

class Routes : Serializable{

    var startingTime: String? = null
    var startingPoint:String? = null
    var endingTime:String? = null
    var endingPoint:String? = null
    var id:String? = null
    var remainingSeats:Long = 0

    constructor()

    constructor(starting_time:String, starting_point:String, ending_time:String, ending_point:String, remaining_seats:Long){
        this.startingTime = starting_time
        this.startingPoint = starting_point
        this.endingTime = ending_time
        this.endingPoint = starting_time
        this.remainingSeats = remaining_seats
    }

    fun toMap():Map<String, Any>{
        val result = HashMap<String, Any>()
        result["startingTime"] = startingTime!!
        result["startingPoint"] = startingPoint!!
        result["endingTime"] = endingTime!!
        result["endingPoint"] = endingPoint!!
        result["seatsRemaining"] = remainingSeats!!
        return result
    }

}
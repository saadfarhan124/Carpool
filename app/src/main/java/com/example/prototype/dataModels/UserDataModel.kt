package com.example.prototype.dataModels

import java.io.Serializable

class UserDataModel :Serializable{
    public var dateOfBirth:String? = null
    public var gender:String? = null

    constructor(dateOfBirth: String, gender:String){
        this.dateOfBirth = dateOfBirth
        this.gender = gender
    }
}
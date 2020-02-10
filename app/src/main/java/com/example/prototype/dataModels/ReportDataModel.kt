package com.example.prototype.dataModels

import java.io.Serializable

class ReportDataModel : Serializable {
    public var repotCategory:String? = null
    public var report:String? = null
    public var userID:String? = null

    constructor(repotCategory: String, report:String, userID:String){
        this.repotCategory = repotCategory
        this.report = report
        this.userID = userID
    }
}
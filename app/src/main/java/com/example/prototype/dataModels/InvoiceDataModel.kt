package com.example.prototype.dataModels

import java.io.Serializable

class InvoiceDataModel : Serializable{
    var invoiceId:Int? = null
    var invoiceNumber: String? = null
    var invoiceAmount: Int? = null
    var date: String? = null
    var userID:String? = null
    var requestID:String? = null

    constructor(invoiceId: Int, invoiceNumber: String, invoiceAmount: Int, date:String, userID: String, requestID:String){
        this.invoiceId = invoiceId
        this.invoiceNumber = invoiceNumber
        this.invoiceAmount = invoiceAmount
        this.date = date
        this.userID = userID
        this.requestID = requestID
    }
    constructor()
}
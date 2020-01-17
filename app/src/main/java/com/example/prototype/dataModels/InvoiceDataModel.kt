package com.example.prototype.dataModels

import java.io.Serializable

class InvoiceDataModel : Serializable{
    var invoiceNumber: Int? = null
    var invoiceAmount: Int? = null
    var date: String? = null
    var userID:String? = null
    var requestID:String? = null

    constructor(invoiceNumber: Int, invoiceAmount: Int, date:String, userID: String, requestID:String){
        this.invoiceNumber = invoiceNumber
        this.invoiceAmount = invoiceAmount
        this.date = date
        this.userID = userID
        this.requestID = requestID
    }
}
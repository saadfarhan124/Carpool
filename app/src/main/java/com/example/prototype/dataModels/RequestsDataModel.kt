package com.example.prototype.dataModels

import java.io.Serializable

class RequestsDataModel : Serializable{
    var daysDataModel: List<DaysDataModel>? = null
    var reviewInformationDataModel: ReviewInformationDataModel? = null

    constructor(daysDataModel: List<DaysDataModel>, reviewInformationDataModel: ReviewInformationDataModel){
        this.daysDataModel = daysDataModel
        this.reviewInformationDataModel = reviewInformationDataModel
    }
}
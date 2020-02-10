package com.example.prototype.dataModels

import java.io.Serializable

class SuggestionDataModel : Serializable {
    public var suggestionType:String? = null
    public var suggestion:String? = null
    public var userID:String? = null

    constructor(suggestionType: String, suggestion:String, userID:String){
        this.suggestionType = suggestionType
        this.suggestion = suggestion
        this.userID = userID
    }
}
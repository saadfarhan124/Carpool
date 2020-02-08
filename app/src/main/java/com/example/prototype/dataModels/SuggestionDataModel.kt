package com.example.prototype.dataModels

import java.io.Serializable

class SuggestionDataModel : Serializable {
    public var typeSuggestion:String? = null
    public var suggestion:String? = null

    constructor(typeSuggestion: String, suggestion:String){
        this.typeSuggestion = typeSuggestion
        this.suggestion = suggestion
    }
}
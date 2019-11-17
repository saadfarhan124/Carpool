package com.example.prototype.ui.newroutes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewroutesViewModel:ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is new routes Fragment"
    }
    val text: LiveData<String> = _text
}
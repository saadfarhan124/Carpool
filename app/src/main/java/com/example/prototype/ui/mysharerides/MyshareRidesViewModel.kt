package com.example.prototype.ui.mysharerides

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyshareRidesViewModel:ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is my rides share Fragment"
    }
    val text: LiveData<String> = _text
}
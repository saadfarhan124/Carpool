package com.example.prototype.ui.packages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PackageViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is package Fragment"
    }
    val text: LiveData<String> = _text
}
package com.example.prototype.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    private var image: Int
        get() {
            return this.image
        }
        set(value) {
            this.image = value
        }

    private var title: Int
        get() {
            return this.title
        }
        set(value) {
            this.title = value
        }

}
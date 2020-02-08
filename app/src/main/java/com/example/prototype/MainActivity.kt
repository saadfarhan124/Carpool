package com.example.prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

import android.content.Context
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter

import java.security.AccessController.getContext


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)
//        val actv: AutoCompleteTextView = findViewById(R.id.outline_exposed_dropdown)
//        val COUNTRIES = arrayOf("Item 1", "Item 2", "Item 3", "Item 4")
//
//
//        val adapter = ArrayAdapter<String>(this, R.layout.activity_customspiner_item, COUNTRIES)
//
//        val editTextFilledExposedDropdown = actv
//        editTextFilledExposedDropdown.setAdapter(adapter)



//        //Top App Bar
//        val toolbar: Toolbar = findViewById(R.id.toolbarprofile)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

//    //Top App Bar Back Nav
//    override fun onSupportNavigateUp():Boolean {
//        onBackPressed()
//        return true
//    }
}

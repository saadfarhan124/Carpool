package com.example.prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

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

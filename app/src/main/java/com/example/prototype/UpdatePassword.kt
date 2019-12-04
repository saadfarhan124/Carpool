package com.example.prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class UpdatePassword : AppCompatActivity() {

//    private lateinit var

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uppass)

        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbarupass)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }
}

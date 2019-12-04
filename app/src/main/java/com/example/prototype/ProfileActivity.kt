package com.example.prototype

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.prototype.Utilities.Util
import com.google.android.material.card.MaterialCardView

class ProfileActivity: AppCompatActivity() {

    private lateinit var updatePasswordCard:MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbarprofile)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        init()
    }

    private fun init(){
        updatePasswordCard = findViewById(R.id.materialCardViewPas)
        updatePasswordCard.setOnClickListener{
           var intent = Intent(applicationContext, UpdatePassword::class.java)
            startActivity(intent)
        }
    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }
}

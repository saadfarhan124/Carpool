package com.example.prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_aftersplash.*
import kotlinx.android.synthetic.main.activity_verifycode.*

class AfterSplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aftersplash)

        signupMobile_btn.setOnClickListener{
            val intent = Intent(this, MobileActivity::class.java)
            startActivity(intent)
        }
        login_btn.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

}

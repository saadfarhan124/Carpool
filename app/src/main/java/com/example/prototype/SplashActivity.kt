package com.example.prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashActivity : AppCompatActivity() {

    private val splashTime = 3000L
    private lateinit var  myHandler: Handler
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        myHandler = Handler()

        myHandler.postDelayed({
            goToAfterSplash()
        },splashTime)
    }
    private fun goToAfterSplash(){
        user = FirebaseAuth.getInstance().currentUser
//        if(user != null){
//            val afterSplashActivityIntent = Intent(applicationContext,AfterSplashActivity::class.java)
//        }else{
//            val homeScreen = Intent(applicationContext,AfterSplashActivity::class.java)
//        }
        val afterSplashActivityIntent = Intent(applicationContext,AfterSplashActivity::class.java)
        startActivity(afterSplashActivityIntent)
        finish()
    }
}

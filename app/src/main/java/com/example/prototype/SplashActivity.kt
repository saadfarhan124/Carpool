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
        val afterSplashActivityIntent = Intent(applicationContext,AfterSplashActivity::class.java)
        val homeScreen = Intent(applicationContext,navdrawer::class.java)
        if(user != null){
            startActivity(homeScreen)
        }else{
            startActivity(afterSplashActivityIntent)
        }
//        val afterSplashActivityIntent = Intent(applicationContext,AfterSplashActivity::class.java)


    }
}

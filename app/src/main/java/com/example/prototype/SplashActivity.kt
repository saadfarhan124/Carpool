package com.example.prototype

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.prototype.Utilities.Util
import com.example.prototype.companion.Companion
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.jetbrains.anko.async

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
            var global = Companion.Globals
            global.user = user
            var options = BitmapFactory.Options()
            Util.getStorageRef().getBytes(Long.MAX_VALUE).addOnSuccessListener {
                Util.getGlobals().userImage = BitmapFactory.decodeByteArray(it,0, it.size, options)
                startActivity(homeScreen)

            }.addOnFailureListener{
                startActivity(homeScreen)
            }

        }else{
            startActivity(afterSplashActivityIntent)
        }
//        val afterSplashActivityIntent = Intent(applicationContext,AfterSplashActivity::class.java)


    }
}

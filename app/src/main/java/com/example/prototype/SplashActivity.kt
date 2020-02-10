package com.example.prototype

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.prototype.Utilities.Util
import com.example.prototype.companion.Companion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.io.File

class SplashActivity : AppCompatActivity() {

    private val splashTime = 4000L


    private lateinit var splashLogo: ImageView
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Function to initiate animation
        animation()
        Handler().postDelayed({
            if(Util.verifyAvailableNetwork(this)){
                goToAfterSplash()
            }else{
                val confirmDialog =
                    AlertDialog.Builder(this, R.style.ThemeOverlay_MaterialComponents_Dialog)
                confirmDialog.setTitle("Sath Chaloo")
                confirmDialog.setMessage("Please connect to internet")
                confirmDialog.setPositiveButton("Ok") { _, _ ->
                    finishAffinity();
                    System.exit(0)
                }
                confirmDialog.show()
            }
        },splashTime)
    }

    private fun animation(){
        //animation
        splashLogo = findViewById(R.id.splash_logo)
        var anim = ScaleAnimation(0.5f, 1f, .5f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.duration = 2000
        anim.fillAfter = true
        splashLogo.startAnimation(anim)
    }

    private fun goToAfterSplash(){
        user = FirebaseAuth.getInstance().currentUser
        val afterSplashActivityIntent = Intent(applicationContext,AfterSplashActivity::class.java)
        val homeScreen = Intent(applicationContext,navdrawer::class.java)
        if(user != null){
            if (user!!.email.isNullOrEmpty()){
                startActivity(Intent(applicationContext, SignupEmailActivity::class.java))
            }else{
                var global = Companion.Globals
                global.user = user

                //Loading picture from database and then saving it locally
                getPreferences(Context.MODE_PRIVATE).getString("saad","saad")
                with(getPreferences(Context.MODE_PRIVATE).edit()){
                    putString("saad", "saad")
                    commit()
                }
                if(getPreferences(Context.MODE_PRIVATE).getString("ImageUri${user!!.uid}","")!!.isNotEmpty()){
                    Util.getGlobals().userImage = BitmapFactory.decodeFile(getPreferences(Context.MODE_PRIVATE).getString("ImageUri${user!!.uid}",""))
                    startActivity(homeScreen)
                }else{
                    val localFile = File.createTempFile("images", "jpg")
                    Util.getStorageRef().getFile(localFile).addOnSuccessListener {
                        with(getPreferences(Context.MODE_PRIVATE).edit()){
                            putString("ImageUri${user!!.uid}", localFile.absolutePath)
                            commit()
                            Util.getGlobals().userImage = BitmapFactory.decodeFile(localFile.absolutePath)
                            startActivity(homeScreen)
                        }
                    }.addOnFailureListener{
                        startActivity(homeScreen)
                    }
                }
            }

        }else{
            startActivity(afterSplashActivityIntent)
        }
    }
}

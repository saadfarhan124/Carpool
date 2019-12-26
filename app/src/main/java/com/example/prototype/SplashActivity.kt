package com.example.prototype

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import com.example.prototype.Utilities.Util
import com.example.prototype.companion.Companion
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.jetbrains.anko.async
import java.io.File

class SplashActivity : AppCompatActivity() {

    private val splashTime = 3000L
    private lateinit var  myHandler: Handler
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        myHandler = Handler()

        myHandler.postDelayed({
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
    private fun goToAfterSplash(){
        user = FirebaseAuth.getInstance().currentUser
        val afterSplashActivityIntent = Intent(applicationContext,AfterSplashActivity::class.java)
        val homeScreen = Intent(applicationContext,navdrawer::class.java)
        if(user != null){
            var global = Companion.Globals
            global.user = user

            //Loading picture from database and then saving it locally
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

                }
            }



        }else{
            startActivity(afterSplashActivityIntent)
        }
    }
}

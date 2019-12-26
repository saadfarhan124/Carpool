package com.example.prototype

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.prototype.Utilities.Util
import com.example.prototype.companion.Companion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbarlogin)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        auth = FirebaseAuth.getInstance()

    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    fun authenticate(v: View){
        var intent = Intent()
        loading.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(lg_txt_email.text.toString(),lg_txt_password.text.toString()).addOnCompleteListener { task ->
            if(task.isSuccessful){
                var globals = Companion.Globals
                globals.user = auth.currentUser
                //Loading picture from database and then saving it locally
                if(getPreferences(Context.MODE_PRIVATE).getString("ImageUri${globals.user!!.uid}","")!!.isNotEmpty()){
                    Util.getGlobals().userImage = BitmapFactory.decodeFile(getPreferences(Context.MODE_PRIVATE).getString("ImageUri${globals.user!!.uid}",""))
                }else{
                    val localFile = File.createTempFile("images", "jpg")
                    Util.getStorageRef().getFile(localFile).addOnSuccessListener {
                        with(getPreferences(Context.MODE_PRIVATE).edit()){
                            putString("ImageUri${globals.user!!.uid}", localFile.absolutePath)
                            commit()
                            Util.getGlobals().userImage = BitmapFactory.decodeFile(localFile.absolutePath)
                            intent = Intent(applicationContext, navdrawer::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)

                        }
                    }.addOnFailureListener{

                    }
                }

            }else{
                Toast.makeText(this,task.exception!!.message, Toast.LENGTH_LONG).show()
            }
            loading.visibility = View.INVISIBLE
        }
    }
}

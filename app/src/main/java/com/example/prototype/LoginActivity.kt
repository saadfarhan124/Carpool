package com.example.prototype

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.prototype.Utilities.Util
import com.example.prototype.companion.Companion
import com.example.prototype.dataModels.UserDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.onClick
import java.io.File


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var forgotPass_btn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbarlogin)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //Firebase Instance
        auth = FirebaseAuth.getInstance()


        //Button Initilization
        forgotPass_btn = findViewById(R.id.forgotPass_btn)
        forgotPass_btn.onClick {
            var intent = Intent(applicationContext, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }


    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun authenticate(v: View) {
        var intent = Intent()
        loading.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(
            lg_txt_email.text.toString(),
            lg_txt_password.text.toString()
        ).addOnFailureListener {
            Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            loading.visibility = View.INVISIBLE
        }
            .addOnSuccessListener { task ->
                var globals = Companion.Globals
                globals.user = auth.currentUser



                //Loading picture from database and then saving it locally
                if (getPreferences(Context.MODE_PRIVATE).getString(
                        "ImageUri${globals.user!!.uid}",
                        ""
                    )!!.isNotEmpty()
                ) {
                    Util.getGlobals().userImage = BitmapFactory.decodeFile(
                        getPreferences(Context.MODE_PRIVATE).getString(
                            "ImageUri${globals.user!!.uid}",
                            ""
                        )
                    )
                    intent = Intent(applicationContext, navdrawer::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    loading.visibility = View.INVISIBLE
                    startActivity(intent)
                } else {
                    val localFile = File.createTempFile("images", "jpg")
                    Util.getStorageRef().getFile(localFile).addOnSuccessListener {
                        with(getPreferences(Context.MODE_PRIVATE).edit()) {
                            putString("ImageUri${globals.user!!.uid}", localFile.absolutePath)
                            commit()
                            Util.getGlobals().userImage =
                                BitmapFactory.decodeFile(localFile.absolutePath)
                            intent = Intent(applicationContext, navdrawer::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            loading.visibility = View.INVISIBLE
                            startActivity(intent)

                        }
                    }.addOnFailureListener {
                        intent = Intent(applicationContext, navdrawer::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        loading.visibility = View.INVISIBLE
                        startActivity(intent)
                    }
                }
            }
    }
}

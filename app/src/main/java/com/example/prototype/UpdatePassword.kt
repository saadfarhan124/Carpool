package com.example.prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.prototype.Utilities.Util
import com.google.firebase.auth.EmailAuthProvider
import org.w3c.dom.Text

class UpdatePassword : AppCompatActivity() {

    private lateinit var oldPassword: TextView
    private lateinit var newPassword: TextView
    private lateinit var confirmPassword: TextView
    private lateinit var btnUpdatePassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uppass)

        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbarupass)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        init()
    }

    private fun init(){
        oldPassword = findViewById(R.id.currentPassword)
        newPassword = findViewById(R.id.newPassword)
        confirmPassword = findViewById(R.id.confirmPassword)
        btnUpdatePassword = findViewById(R.id.btnUpdatePassword)
        btnUpdatePassword.setOnClickListener{
            if(confirmPassword.text.toString() == "" || oldPassword.text.toString() == "" || newPassword.text.toString() == ""){
                Toast.makeText(applicationContext, "Please fill all the fields", Toast.LENGTH_LONG).show()
            }else if(confirmPassword.text.toString() != newPassword.text.toString()){
                Toast.makeText(applicationContext, "Passwords do not match", Toast.LENGTH_LONG).show()
            }else{

                var user = Util.getGlobals().user
                val credential = EmailAuthProvider.getCredential(user!!.email.toString(), oldPassword.text.toString())
                user.reauthenticate(credential).addOnSuccessListener{
                    user.updatePassword(newPassword.text.toString()).addOnSuccessListener {
                        Toast.makeText(applicationContext, "Password changed successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Util.logout(applicationContext))
                    }.addOnFailureListener{
                        Toast.makeText(applicationContext, "Password must be greater than 6 digits", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener{
                    Toast.makeText(applicationContext, "Provided details not correct", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }
}

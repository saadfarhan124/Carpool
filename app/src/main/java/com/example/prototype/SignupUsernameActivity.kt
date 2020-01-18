package com.example.prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_signup_username.*


class SignupUsernameActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_username)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        loading.visibility = View.INVISIBLE

        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbaruser)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    fun profileSubmit(v : View){
        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(sg_txt_username.text.toString()).build()
        user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
            if(task.isSuccessful){
                var intent = Intent(applicationContext, SelectGenderAndDob::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,task.exception!!.message, Toast.LENGTH_LONG).show()
            }
        }
    }


}
package com.example.prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_signup_email.*


class SignupEmailActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_email)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!


        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbaremail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    fun continueFromEmail(v : View){
        var intent = Intent()
        loading.visibility = View.VISIBLE
        if (isEmailValid(sg_txt_email.text.toString())){
            user!!.updateEmail(sg_txt_email.text.toString())?.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    intent = Intent(applicationContext, SignupPasswordActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(applicationContext, task!!.exception.toString(), Toast.LENGTH_LONG).show()
                }
                loading.visibility = View.INVISIBLE
            }
        }else{
            Toast.makeText(applicationContext, "Email not valid", Toast.LENGTH_SHORT).show()
        }

    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
package com.example.prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_signup_password.*


class SignupPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_password)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        loading.visibility = View.INVISIBLE

        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbarpass)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        confirm_password.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Toast.makeText(applicationContext, "Woohoo", Toast.LENGTH_LONG)
                loading.visibility = View.VISIBLE
                user!!.updatePassword(sg_txt_password.text.toString()).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"Success", Toast.LENGTH_LONG)
                        var intent = Intent(applicationContext, SignupUsernameActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(applicationContext,task.exception!!.message,Toast.LENGTH_LONG).show()
                    }
                    loading.visibility = View.INVISIBLE
                }
            }

        })
    }
    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    fun demo(first:String, arr:Array<String>){

    }


}
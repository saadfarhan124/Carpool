package com.example.prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        auth.signOut()
    }
    fun authenticate(v: View){
        var intent: Intent = Intent()
        loading.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(lg_txt_email.text.toString(),lg_txt_password.text.toString()).addOnCompleteListener { task ->
            if(task.isSuccessful){
                intent = Intent(applicationContext, navdrawer::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,task.exception!!.message, Toast.LENGTH_LONG).show()
            }
            loading.visibility = View.INVISIBLE
        }
    }
}

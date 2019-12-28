package com.example.prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.prototype.Utilities.Util
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var btn_frg: Button
    private lateinit var edit_em:TextView
    private lateinit var loading:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_em)


        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbarforgotpass)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //Intilization
        edit_em = findViewById(R.id.edit_em)

        loading = findViewById(R.id.loading)

        btn_frg = findViewById(R.id.btn_frg)
        btn_frg.onClick {
            if (edit_em.text.isNotEmpty()){
                if(Util.isEmailValid(edit_em.text.toString())){
                    loading.visibility = View.VISIBLE
                    //Password Recovery
                    Util.getFirebaseAuth().sendPasswordResetEmail(edit_em.text.toString())
                        .addOnSuccessListener {
                            edit_em.text = ""
                            loading.visibility = View.INVISIBLE
                            Toast.makeText(applicationContext, "Email sent", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener{
                            Toast.makeText(applicationContext, "Email does not exist", Toast.LENGTH_LONG).show()
                            edit_em.text = ""
                            loading.visibility = View.INVISIBLE

                        }
                }else{
                    Toast.makeText(applicationContext, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(applicationContext, "Please enter your email address", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }
}

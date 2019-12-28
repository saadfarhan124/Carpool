package com.example.prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.prototype.Utilities.Util
import org.jetbrains.anko.onClick

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var btn_frg: Button
    private lateinit var edit_em:TextView

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

        btn_frg = findViewById(R.id.btn_frg)
        btn_frg.onClick {
            if (edit_em.text.isNotEmpty()){
                if(Util.isEmailValid(edit_em.text.toString())){

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

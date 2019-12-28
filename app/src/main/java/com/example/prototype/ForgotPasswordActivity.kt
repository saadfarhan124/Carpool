package com.example.prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
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
            Toast.makeText(applicationContext, edit_em.text.toString(), Toast.LENGTH_LONG).show()
        }
    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }
}

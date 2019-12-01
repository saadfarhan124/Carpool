package com.example.prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_mobile.*
class MobileActivity : AppCompatActivity() {

    //Auth Object
    lateinit var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile)

        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbarmbl)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }


    fun verify(v : View){
        var intent = Intent(applicationContext, VerifyCodeActivity::class.java)
        intent.putExtra("Number","+92"+lgd.text.toString() )
        startActivity(intent)

    }
}

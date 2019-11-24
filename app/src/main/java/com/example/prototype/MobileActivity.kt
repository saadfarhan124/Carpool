package com.example.prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_mobile.*
class MobileActivity : AppCompatActivity() {

    //Auth Object
    lateinit var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile)

    }


    fun verify(v : View){
        var intent = Intent(applicationContext, VerifyCodeActivity::class.java)
        intent.putExtra("Number","+92"+lgd.text.toString() )
        startActivity(intent)

    }
}

package com.example.prototype

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_verifycode.*
import java.util.concurrent.TimeUnit

class VerifyCodeActivity : AppCompatActivity() {

    //Fire Base Auth, Phone Auth Callbacks, Verification ID
    private lateinit var auth: FirebaseAuth
    lateinit var mCallBacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var verificationId : String? = null
    var sharedPreferences:SharedPreferences? = null

    //Function to verify code
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

                    val user = task.result?.user
                    var intent:Intent = Intent()

                    when(user!!.email){
                        null -> intent = Intent(applicationContext, SignupEmailActivity::class.java)
                    }
//                    progressBar.visibility = View.INVISIBLE
                    startActivity(intent)
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Invalid" + credential.smsCode , Toast.LENGTH_SHORT).show()
                        // The verification code entered was invalid
                    }
                }
            }
    }

    //Function to handle Phone Authentication Callbacks
    private fun verificationCallbacks(){
        mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                if(p0.smsCode != null){
                    signInWithPhoneAuthCredential(p0)
                }
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                verificationId = p0
//                progressBar.visibility = View.INVISIBLE
            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifycode)

        //Shared Pref
        sharedPreferences = this.getSharedPreferences("com.example.carpool", Context.MODE_PRIVATE)

        //Phone Authentication
        verificationCallbacks()

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            intent.getStringExtra("Number") ,
            60,
            TimeUnit.SECONDS,
            this,
            mCallBacks
        )
//        progressBar.visibility = View.VISIBLE

        //Firebase auth
        auth = FirebaseAuth.getInstance()

        //pinView
        pinView.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s != null && s.length == 6){
                    val credential = PhoneAuthProvider.getCredential(verificationId!!, s.toString())
                    signInWithPhoneAuthCredential(credential)
                }
            }

        })
    }
}


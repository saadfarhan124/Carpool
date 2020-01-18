package com.example.prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.skyfishjy.library.RippleBackground
import org.jetbrains.anko.onClick

class SelectGenderAndDob : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)

        val rippleBackground = findViewById<RippleBackground>(R.id.content)

        var btnContinue = findViewById<Button>(R.id.btnContinue)
        btnContinue.onClick {
            rippleBackground.startRippleAnimation()
        }
    }
}

package com.example.prototype

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_ticket.*

class TicketActvity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)


        btn_return.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.activity_returnbooking_bottomsheet, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }

//        btn_return.setOnClickListener {
//            val bottomSheetFragment = BottomSheetFragment()
//            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
//        }
    }
}
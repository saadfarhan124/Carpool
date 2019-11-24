package com.example.prototype

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.prototype.dataModels.Routes
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_ticket.*

class TicketActvity: AppCompatActivity(){

    private lateinit var startingTime: TextView
    private lateinit var endingTime: TextView
    private lateinit var startingPoint: TextView
    private lateinit var endingPoint: TextView
    private lateinit var seatsRemaining: TextView
    private lateinit var scheduledDate: TextView

    private lateinit var routeObject: Routes
    private lateinit var progressBar: ProgressBar
    private val TAG = "Ticket Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

    }
}
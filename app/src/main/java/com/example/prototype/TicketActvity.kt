package com.example.prototype

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.prototype.dataModels.Booking
import com.example.prototype.dataModels.Routes
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_ticket.*

class TicketActvity: AppCompatActivity(){

    private lateinit var startingTime: TextView
    private lateinit var endingTime: TextView
    private lateinit var startingPoint: TextView
    private lateinit var endingPoint: TextView
    private lateinit var seatsBooked: TextView
    private lateinit var scheduledDate: TextView
    private lateinit var bookingID: TextView

    private lateinit var bookingObject: Booking
    private lateinit var progressBar: ProgressBar
    private val TAG = "Ticket Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)


        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbartck)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        bookingObject = intent.extras!!.getSerializable("bookingDetails") as Booking
        initilizeVariables()
        startingTime.text = bookingObject.startingTime
        endingTime.text = bookingObject.endingTime
        startingPoint.text = bookingObject.startingPoint
        endingPoint.text = bookingObject.endingPoint
        seatsBooked.text = bookingObject.numberOfSeats.toString()
        scheduledDate.text = bookingObject.bookingDate
        bookingID.text = bookingObject.bookingId.toString()
    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }


    private fun initilizeVariables(){
        startingTime = findViewById(R.id.ticketStartTime)
        endingTime = findViewById(R.id.ticketEndTime)
        startingPoint = findViewById(R.id.ticketStartingLocation)
        endingPoint = findViewById(R.id.ticketEndingLocation)
        seatsBooked = findViewById(R.id.ticketNumberOfSeats)
        scheduledDate = findViewById(R.id.ticketDate)
        bookingID = findViewById(R.id.ticketBookingId)
    }
}
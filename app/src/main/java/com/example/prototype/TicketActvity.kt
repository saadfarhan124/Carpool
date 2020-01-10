package com.example.prototype

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.prototype.dataModels.Booking
import org.jetbrains.anko.onClick

class TicketActvity: AppCompatActivity(){

    private lateinit var startingTime: TextView
    private lateinit var endingTime: TextView
    private lateinit var startingPoint: TextView
    private lateinit var endingPoint: TextView
    private lateinit var seatsBooked: TextView
    private lateinit var scheduledDate: TextView
    private lateinit var bookingID: TextView
    private lateinit var findStop:Button
    private lateinit var btnTrackRide:Button

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

    override fun onBackPressed() {
        intent = Intent(applicationContext, navdrawer::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }


    private fun initilizeVariables(){
        startingTime = findViewById(R.id.ticketStartTime)
        endingTime = findViewById(R.id.ticketEndTime)
        startingPoint = findViewById(R.id.ticketStartingLocation)
        endingPoint = findViewById(R.id.ticketEndingLocation)
        seatsBooked = findViewById(R.id.ticketNumberOfSeats)
        scheduledDate = findViewById(R.id.ticketDate)
        bookingID = findViewById(R.id.ticketBookingId)
        findStop = findViewById(R.id.findStop)
        findStop.setOnClickListener{
            val intent = Intent(applicationContext, FindStopActivity::class.java)
            intent.putExtra("stopLat", bookingObject.pickupLat)
            intent.putExtra("stopLong", bookingObject.pickupLong)
            startActivity(intent)
        }
        btnTrackRide = findViewById(R.id.btnTrack)
        btnTrackRide.onClick {
            val intent = Intent(applicationContext, TrackRideActivity::class.java)
            intent.putExtra("routeId", bookingObject.routeId)
            startActivity(intent)
        }
    }
}
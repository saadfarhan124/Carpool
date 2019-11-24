package com.example.prototype

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.prototype.dataModels.Routes
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class BookingActivity : AppCompatActivity() {

    private lateinit var startingTime:TextView
    private lateinit var endingTime:TextView
    private lateinit var startingPoint:TextView
    private lateinit var endingPoint:TextView
    private lateinit var seatsRemaining:TextView
    private lateinit var scheduledDate:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        initializeVariables()
        var routeObject = intent.extras!!.getSerializable("routeDetails") as Routes
        startingTime.text = routeObject.startingTime
        endingTime.text = routeObject.endingTime
        startingPoint.text = routeObject.startingPoint
        endingPoint.text = routeObject.endingPoint
        seatsRemaining.text = routeObject.remainingSeats.toString()
        scheduledDate.text = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
    }

    fun initializeVariables(){
        startingTime = findViewById(R.id.bookingStartTime)
        endingTime = findViewById(R.id.bookingEndingTime)
        startingPoint = findViewById(R.id.bookingStartLocation)
        endingPoint = findViewById(R.id.bookingEndingLocation)
        seatsRemaining = findViewById(R.id.bookingSeatsRemaining)
        scheduledDate = findViewById(R.id.scheduled_Date)
    }
}

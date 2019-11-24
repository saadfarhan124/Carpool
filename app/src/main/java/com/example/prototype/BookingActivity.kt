package com.example.prototype

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.prototype.dataModels.Routes
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.AlertDialogBuilder
import org.jetbrains.anko.find
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class BookingActivity : AppCompatActivity() {

    private lateinit var startingTime: TextView
    private lateinit var endingTime: TextView
    private lateinit var startingPoint: TextView
    private lateinit var endingPoint: TextView
    private lateinit var seatsRemaining: TextView
    private lateinit var scheduledDate: TextView
    private lateinit var originalFare: TextView
    private lateinit var calculatedFare: TextView
    private lateinit var seatsSpinner: Spinner
    private lateinit var btnBookNow: Button
    private lateinit var routeObject: Routes
    private val TAG = "Booking Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        initializeVariables()
        routeObject = intent.extras!!.getSerializable("routeDetails") as Routes
        startingTime.text = routeObject.startingTime
        endingTime.text = routeObject.endingTime
        startingPoint.text = routeObject.startingPoint
        endingPoint.text = routeObject.endingPoint
        seatsRemaining.text = routeObject.remainingSeats.toString()
        scheduledDate.text = LocalDateTime.now().plusDays(1)
            .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
        initializeSpinner(routeObject.remainingSeats)
    }

    private fun initializeVariables() {
        startingTime = findViewById(R.id.bookingStartTime)
        endingTime = findViewById(R.id.bookingEndingTime)
        startingPoint = findViewById(R.id.bookingStartLocation)
        endingPoint = findViewById(R.id.bookingEndingLocation)
        seatsRemaining = findViewById(R.id.bookingSeatsRemaining)
        scheduledDate = findViewById(R.id.scheduled_Date)
        originalFare = findViewById(R.id.original_fare)
        calculatedFare = findViewById(R.id.calculatedFare)
        btnBookNow = findViewById(R.id.btnBookNow)
        btnBookNow.setOnClickListener { bookARide() }

    }

    private fun bookARide() {
//        MaterialAlertDialogBuilder(applicationContext, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog)
//            .setTitle("Title")
//            .setMessage("Message")
//            .setPositiveButton("Ok", null)
//            .show();
        val confirmDialog = AlertDialog.Builder(this, R.style.ThemeOverlay_MaterialComponents_Dialog)
        confirmDialog.setTitle("Sath Chaloo")
        confirmDialog.setMessage("Are you sure you want to proceed with your booking?")
        confirmDialog.setPositiveButton("Yes") { _, _ ->
            Toast.makeText(applicationContext, "Clicked positive button", Toast.LENGTH_LONG).show()
            val docRef = FirebaseFirestore.getInstance().collection("routes").document(routeObject.id!!)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document["ending_point"]}")
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }

        }
        confirmDialog.setNegativeButton("No") { _, _ ->
            Toast.makeText(applicationContext, "Clicked negative button", Toast.LENGTH_LONG).show()
        }
        confirmDialog.show()
//        val intent = Intent(applicationContext, TicketActvity::class.java)
//        startActivity(intent)
    }

    private fun initializeSpinner(capacity: Long) {
        seatsSpinner = findViewById(R.id.seats_spinner)
        var numberofSeats = mutableListOf<Long>()
        for (i in 1..capacity) {
            numberofSeats.add(i)
        }
        val adapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_item,
            numberofSeats
        )
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        seatsSpinner.adapter = adapter
        seatsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                calculatedFare.text =
                    (originalFare.text.toString().toLong() * parent!!.getItemAtPosition(position).toString().toLong()).toString()
            }

        }

    }
}

package com.example.prototype

import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.prototype.Utilities.Util
import com.example.prototype.dataModels.CarSharingDataModel
import com.example.prototype.dataModels.DaysDataModel
import com.example.prototype.dataModels.ReviewInformationDataModel
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.DocumentReference
import com.skyfishjy.library.RippleBackground
import org.jetbrains.anko.enabled
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick

class ReviewInformationActivity : AppCompatActivity() {

    private var MIC_STATUS = 0

    //Text Views
    private lateinit var txt_pickupAddress: TextView
    private lateinit var txt_dropoffAddress: TextView
    private lateinit var txt_name: TextView
    private lateinit var txt_number: TextView
    private lateinit var txt_startTime: TextView
    private lateinit var txt_returnTime: TextView

    //Chips
    private lateinit var txt_chip_Mon: Chip
    private lateinit var txt_chip_Tue: Chip
    private lateinit var txt_chip_Wed: Chip
    private lateinit var txt_chip_Thu: Chip
    private lateinit var txt_chip_Fri: Chip
    private lateinit var txt_chip_Sat: Chip
    private lateinit var txt_chip_Sun: Chip

    //DataModels
    private lateinit var DaysData: DaysDataModel
    private lateinit var ReviewData: ReviewInformationDataModel
    private lateinit var TimeDetails: List<CarSharingDataModel>

    //Image View
    private lateinit var ImageAc: ImageView
    private lateinit var ImageNonAc: ImageView

    //Ripples
    private lateinit var standardRippleBackground: RippleBackground
    private lateinit var premiumRippleBackground: RippleBackground

    //Submit Button
    private lateinit var btn_submitReq: Button

    //Progress Bar
    private lateinit var reviewInfoProgressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviewinfo)

        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbarsp)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        init()
    }

    private fun init() {
        reviewInfoProgressBar = findViewById(R.id.reviewInfoProgressBar)
        //DAta Models
        DaysData = intent.extras!!.get("DaysData") as DaysDataModel
        ReviewData = intent.extras!!.get("ReviewData") as ReviewInformationDataModel
        TimeDetails = intent.extras!!.getSerializable("TimeDetails") as List<CarSharingDataModel>


        //Text Views
        txt_pickupAddress = findViewById(R.id.textViewPickUpAddress)
        txt_pickupAddress.text = ReviewData.pickUpAddress

        txt_dropoffAddress = findViewById(R.id.textViewDropOffAddress)
        txt_dropoffAddress.text = ReviewData.dropOffAddress

        txt_name = findViewById(R.id.txt_name)
        txt_name.text = Util.getGlobals().user!!.displayName

        txt_number = findViewById(R.id.txt_number)
        txt_number.text = Util.getGlobals().user!!.phoneNumber

        txt_startTime = findViewById(R.id.txt_startTime)
        txt_returnTime = findViewById(R.id.txt_returnTime)

        btn_submitReq = findViewById(R.id.btn_submitReq)
        btn_submitReq.onClick {
            submitRequest()
        }


        //Chips
        txt_chip_Mon = findViewById(R.id.txt_chip_Mon)
        txt_chip_Mon.enabled = DaysData.Monday
        if (DaysData.Monday) {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Monday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Monday" }!!.dropOffTime
        }
        txt_chip_Mon.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Monday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Monday" }!!.dropOffTime
        }

        txt_chip_Tue = findViewById(R.id.txt_chip_Tue)
        txt_chip_Tue.enabled = DaysData.Tuesday
        if (DaysData.Tuesday) {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Tuesday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Tuesday" }!!.dropOffTime
        }
        txt_chip_Tue.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Tuesday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Tuesday" }!!.dropOffTime
        }

        txt_chip_Wed = findViewById(R.id.txt_chip_Wed)
        txt_chip_Wed.enabled = DaysData.Wednesday
        if (DaysData.Wednesday) {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Wednesday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Wednesday" }!!.dropOffTime
        }
        txt_chip_Wed.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Wednesday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Wednesday" }!!.dropOffTime
        }

        txt_chip_Thu = findViewById(R.id.txt_chip_Thu)
        txt_chip_Thu.enabled = DaysData.Thursday
        if (DaysData.Thursday) {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Thursday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Thursday" }!!.dropOffTime
        }
        txt_chip_Thu.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Thursday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Thursday" }!!.dropOffTime
        }

        txt_chip_Fri = findViewById(R.id.txt_chip_Fri)
        txt_chip_Fri.enabled = DaysData.Friday
        if (DaysData.Friday) {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Friday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Friday" }!!.dropOffTime
        }
        txt_chip_Fri.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Friday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Friday" }!!.dropOffTime
        }

        txt_chip_Sat = findViewById(R.id.txt_chip_Sat)
        txt_chip_Sat.enabled = DaysData.Saturday
        if (DaysData.Saturday) {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Saturday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Saturday" }!!.dropOffTime
        }
        txt_chip_Sat.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Saturday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Saturday" }!!.dropOffTime
        }

        txt_chip_Sun = findViewById(R.id.txt_chip_Sun)
        txt_chip_Sun.enabled = DaysData.Sunday
        if (DaysData.Sunday) {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Sunday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Sunday" }!!.dropOffTime
        }
        txt_chip_Sun.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Sunday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find { e -> e.day == "Sunday" }!!.dropOffTime
        }
    }

    private fun submitRequest() {

        var alertDialog = Util.getAlertDialog(this)
        alertDialog.setMessage("Do you want to submit this request?")
        alertDialog.setPositiveButton("Yes") { _, _ ->
            reviewInfoProgressBar.visibility = View.VISIBLE
            Util.getFirebaseFireStore().collection("request_id")
                .get()
                .addOnSuccessListener {
                    var requestID = it.documents[0]["requestId"].toString().toInt()
                    Util.getFirebaseFireStore().collection("request_id")
                        .document(it.documents[0].id)
                        .update("requestId", requestID + 1)
                        .addOnSuccessListener {
                            var ref: DocumentReference =
                                Util.getFirebaseFireStore().collection("carRideRequests")
                                    .document(requestID.toString())
                            for (item in TimeDetails) {
                                ref.collection("Days").document(item.day!!).set(item)
                            }
                            ref.set(ReviewData).addOnCompleteListener {
                                reviewInfoProgressBar.visibility = View.INVISIBLE
                                Toast.makeText(
                                    applicationContext,
                                    "Requests sent",
                                    Toast.LENGTH_LONG
                                ).show()
                                var intent = Intent(applicationContext, navdrawer::class.java)
                                startActivity(intent)
                            }
                        }
                }

        }
        alertDialog.setNegativeButton("No") { _, _ ->

        }
        alertDialog.show()
    }


    //Top App Bar Back Nav
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

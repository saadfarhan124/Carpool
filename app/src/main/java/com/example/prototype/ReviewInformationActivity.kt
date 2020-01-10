package com.example.prototype

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.prototype.Utilities.Util
import com.example.prototype.dataModels.CarSharingDataModel
import com.example.prototype.dataModels.DaysDataModel
import com.example.prototype.dataModels.ReviewInformationDataModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_reviewinfo.*
import org.jetbrains.anko.enabled
import org.jetbrains.anko.onClick

class ReviewInformationActivity : AppCompatActivity() {

    private var MIC_STATUS = 0

    //Text Views
    private lateinit var txt_pickupAddress:TextView
    private lateinit var txt_dropoffAddress:TextView
    private lateinit var txt_name:TextView
    private lateinit var txt_number:TextView
    private lateinit var txt_startTime:TextView
    private lateinit var txt_returnTime:TextView

    //Chips
    private lateinit var txt_chip_Mon: Chip
    private lateinit var txt_chip_Tue: Chip
    private lateinit var txt_chip_Wed: Chip
    private lateinit var txt_chip_Thu: Chip
    private lateinit var txt_chip_Fri: Chip
    private lateinit var txt_chip_Sat: Chip
    private lateinit var txt_chip_Sun: Chip

    //DataModels
    private lateinit var DaysData:DaysDataModel
    private lateinit var ReviewData:ReviewInformationDataModel
    private lateinit var TimeDetails: List<CarSharingDataModel>

    //Image View
    private lateinit var ImageAc: ImageView
    private lateinit var ImageNonAc: ImageView


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

    private fun init(){
        //DAta Models
        DaysData = intent.extras!!.get("DaysData") as DaysDataModel
        ReviewData = intent.extras!!.get("ReviewData") as ReviewInformationDataModel
        TimeDetails = intent.extras!!.getSerializable("TimeDetails") as List<CarSharingDataModel>


        //Text Views
        txt_pickupAddress = findViewById(R.id.txt_pickupAddress)
        txt_pickupAddress.text = ReviewData.pickUpAddress

        txt_dropoffAddress = findViewById(R.id.txt_dropoffAddress)
        txt_dropoffAddress.text = ReviewData.dropOffAddress

        txt_name = findViewById(R.id.txt_name)
        txt_name.text = Util.getGlobals().user!!.displayName

        txt_number = findViewById(R.id.txt_number)
        txt_number.text = Util.getGlobals().user!!.phoneNumber

        txt_startTime = findViewById(R.id.txt_startTime)
        txt_returnTime = findViewById(R.id.txt_returnTime)

        //Image
        ImageAc = findViewById(R.id.img_ac)
        ImageNonAc = findViewById(R.id.img_nonAC)

        ImageAc!!.setOnClickListener(object:View.OnClickListener {
            override fun onClick(v: View?) {
                if(MIC_STATUS == 0){
                    img_ac.setColorFilter(Color.rgb(255,42,72))
                    MIC_STATUS = 1
                }else if(MIC_STATUS == 1){
                    img_ac.setColorFilter(Color.WHITE)
                    MIC_STATUS = 0
                }
            }})

        ImageNonAc!!.setOnClickListener(object:View.OnClickListener {
            override fun onClick(v: View?) {
                if(MIC_STATUS == 0){
                    ImageNonAc.setColorFilter(Color.rgb(255,42,72))
                    MIC_STATUS = 1
                }else if(MIC_STATUS == 1){
                    ImageNonAc.setColorFilter(Color.WHITE)
                    MIC_STATUS = 0
                }
            }})


        //Chips
        txt_chip_Mon = findViewById(R.id.txt_chip_Mon)
        txt_chip_Mon.enabled = DaysData.Monday
        if(DaysData.Monday){
            txt_startTime.text = TimeDetails.find { e -> e.day == "Monday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Monday"}!!.dropOffTime
        }
        txt_chip_Mon.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Monday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Monday"}!!.dropOffTime
        }

        txt_chip_Tue = findViewById(R.id.txt_chip_Tue)
        txt_chip_Tue.enabled = DaysData.Tuesday
        if(DaysData.Tuesday){
            txt_startTime.text = TimeDetails.find { e -> e.day == "Tuesday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Tuesday"}!!.dropOffTime
        }
        txt_chip_Tue.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Tuesday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Tuesday"}!!.dropOffTime
        }

        txt_chip_Wed = findViewById(R.id.txt_chip_Wed)
        txt_chip_Wed.enabled = DaysData.Wednesday
        if(DaysData.Wednesday){
            txt_startTime.text = TimeDetails.find { e -> e.day == "Wednesday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Wednesday"}!!.dropOffTime
        }
        txt_chip_Wed.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Wednesday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Wednesday"}!!.dropOffTime
        }

        txt_chip_Thu = findViewById(R.id.txt_chip_Thu)
        txt_chip_Thu.enabled = DaysData.Thursday
        if(DaysData.Thursday){
            txt_startTime.text = TimeDetails.find { e -> e.day == "Thursday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Thursday"}!!.dropOffTime
        }
        txt_chip_Thu.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Thursday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Thursday"}!!.dropOffTime
        }

        txt_chip_Fri = findViewById(R.id.txt_chip_Fri)
        txt_chip_Fri.enabled = DaysData.Friday
        if(DaysData.Friday){
            txt_startTime.text = TimeDetails.find { e -> e.day == "Friday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Friday"}!!.dropOffTime
        }
        txt_chip_Fri.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Friday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Friday"}!!.dropOffTime
        }

        txt_chip_Sat = findViewById(R.id.txt_chip_Sat)
        txt_chip_Sat.enabled = DaysData.Saturday
        if(DaysData.Saturday){
            txt_startTime.text = TimeDetails.find { e -> e.day == "Saturday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Saturday"}!!.dropOffTime
        }
        txt_chip_Sat.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Saturday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Saturday"}!!.dropOffTime
        }

        txt_chip_Sun = findViewById(R.id.txt_chip_Sun)
        txt_chip_Sun.enabled = DaysData.Sunday
        if(DaysData.Sunday){
            txt_startTime.text = TimeDetails.find { e -> e.day == "Sunday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Sunday"}!!.dropOffTime
        }
        txt_chip_Sun.onClick {
            txt_startTime.text = TimeDetails.find { e -> e.day == "Sunday" }!!.pickUpTime
            txt_returnTime.text = TimeDetails.find{ e -> e.day == "Sunday"}!!.dropOffTime
        }
    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }
}

package com.example.prototype

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.prototype.Utilities.Util
import com.example.prototype.dataModels.CarSharingDataModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.DocumentReference
import com.jakewharton.threetenabp.AndroidThreeTen
import org.jetbrains.anko.onClick
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class UpdateDaysAndTime : AppCompatActivity() {

    //Chips
    private lateinit var Monday: Chip
    private lateinit var Tuesday: Chip
    private lateinit var Wednesday: Chip
    private lateinit var Thursday: Chip
    private lateinit var Friday: Chip
    private lateinit var Saturday: Chip
    private lateinit var Sunday: Chip

    //Material Cards
    private lateinit var MondayCard: MaterialCardView
    private lateinit var TuesdayCard: MaterialCardView
    private lateinit var WednesdayCard: MaterialCardView
    private lateinit var ThursdayCard: MaterialCardView
    private lateinit var FridayCard: MaterialCardView
    private lateinit var SaturdayCard: MaterialCardView
    private lateinit var SundayCard: MaterialCardView

    //select time pickups
    private lateinit var mondayPickUpTextView: TextView
    private lateinit var tuesdayPickUpTextView: TextView
    private lateinit var wednesdayPickUpTextView: TextView
    private lateinit var thursdayPickUpTextView: TextView
    private lateinit var fridayPickUpTextView: TextView
    private lateinit var saturdayPickUpTextView: TextView
    private lateinit var sundayPickUpTextView: TextView

    //select time dropoffs
    private lateinit var mondayDropOffTextView: TextView
    private lateinit var tuesdayDropOffTextView: TextView
    private lateinit var wednesdayDropOffTextView: TextView
    private lateinit var thursdayDropOffTextView: TextView
    private lateinit var fridayDropOffTextView: TextView
    private lateinit var saturdayDropOffTextView: TextView
    private lateinit var sundayDropOffTextView: TextView

    //time pickup
    private lateinit var mondayTimePickUp: TextView
    private lateinit var tuesdayTimePickUp: TextView
    private lateinit var wednesdayTimePickUp: TextView
    private lateinit var thursdayTimePickUp: TextView
    private lateinit var fridayTimePickUp: TextView
    private lateinit var saturdayTimePickUp: TextView
    private lateinit var sundayTimePickUp: TextView

    //time dropoffs
    private lateinit var mondayTimeDropOff: TextView
    private lateinit var tuesdayTimeDropOff: TextView
    private lateinit var wednesdayTimeDropOff: TextView
    private lateinit var thursdayTimeDropOff: TextView
    private lateinit var fridayTimeDropOff: TextView
    private lateinit var saturdayTimeDropOff: TextView
    private lateinit var sundayTimeDropOff: TextView

    //Button
    private lateinit var btnContinue: Button

    //Progress Bar
    private lateinit var updateDaysTimeProgressBar: ProgressBar

    //Calendar variable
    private lateinit var calendar: Calendar

    //List of days
    private lateinit var listOfDaysBooked: ArrayList<CarSharingDataModel>

    //Request ID
    private  var requestID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectdays)
        AndroidThreeTen.init(this)

        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbarsp)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        calendar = Util.getCalendarInstance()

        init()
        getDatabaseEntries()
    }

    private fun updateData() {
        updateDaysTimeProgressBar.visibility = View.VISIBLE
        var listOfDays = mutableListOf<CarSharingDataModel>()
        //Monday
        if (Monday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
            if (mondayTimePickUp.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select pick up time for Monday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else if (mondayTimeDropOff.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select drop off time for Monday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else {
                var carSharingDataModel = CarSharingDataModel(
                    "Monday",
                    mondayTimePickUp.text.toString(),
                    mondayTimeDropOff.text.toString()
                )
                listOfDays.add(carSharingDataModel)
            }
        }
        //Tuesday
        if (Tuesday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
            if (tuesdayTimePickUp.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select pick up time for Tuesday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else if (tuesdayTimeDropOff.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select drop off time for Tuesday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else {
                var carSharingDataModel = CarSharingDataModel(
                    "Tuesday",
                    tuesdayTimePickUp.text.toString(),
                    tuesdayTimeDropOff.text.toString()
                )
                listOfDays.add(carSharingDataModel)
            }
        }
        //Wednesday
        if (Wednesday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
            if (wednesdayTimePickUp.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select pick up time for Wednesday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else if (wednesdayTimeDropOff.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select drop off time for Wednesday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else {
                var carSharingDataModel = CarSharingDataModel(
                    "Wednesday",
                    wednesdayTimePickUp.text.toString(),
                    wednesdayTimeDropOff.text.toString()
                )
                listOfDays.add(carSharingDataModel)
            }
        }

        //Thursday
        if (Thursday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
            if (thursdayTimePickUp.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select pick up time for Thursday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else if (thursdayTimeDropOff.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select drop off time for Thursday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else {
                var carSharingDataModel = CarSharingDataModel(
                    "Thursday",
                    thursdayTimePickUp.text.toString(),
                    thursdayTimeDropOff.text.toString()
                )
                listOfDays.add(carSharingDataModel)
            }
        }

        //Friday
        if (Friday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
            if (fridayTimePickUp.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select pick up time for Friday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else if (fridayTimeDropOff.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select drop off time for Friday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else {
                var carSharingDataModel = CarSharingDataModel(
                    "Friday",
                    fridayTimePickUp.text.toString(),
                    fridayTimeDropOff.text.toString()
                )
                listOfDays.add(carSharingDataModel)
            }
        }

        //Saturday
        if (Saturday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
            if (saturdayTimePickUp.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select pick up time for Saturday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else if (saturdayTimeDropOff.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select drop off time for Saturday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else {
                var carSharingDataModel = CarSharingDataModel(
                    "Saturday",
                    saturdayTimePickUp.text.toString(),
                    saturdayTimeDropOff.text.toString()
                )
                listOfDays.add(carSharingDataModel)
            }
        }

        //Sunday
        if (Sunday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
            if (sundayTimePickUp.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select pick up time for Sunday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else if (sundayTimeDropOff.text == "") {
                Toast.makeText(
                    applicationContext,
                    "Please select drop off time for Sunday",
                    Toast.LENGTH_LONG
                ).show()
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                return
            } else {
                var carSharingDataModel = CarSharingDataModel(
                    "Sunday",
                    sundayTimePickUp.text.toString(),
                    sundayTimeDropOff.text.toString()
                )
                listOfDays.add(carSharingDataModel)
            }
        }
        var ref: DocumentReference =
            Util.getFirebaseFireStore().collection("carRideRequests").document(requestID)
        ref.collection("Days")
            .get()
            .addOnSuccessListener {
                for(document in it.documents){
                    ref.collection("Days").document(document.id).delete()
                }
            }.addOnSuccessListener {
                for (item in listOfDays) {
                    ref.collection("Days").document(item.day!!).set(item)
                }
                updateDaysTimeProgressBar.visibility = View.INVISIBLE
                Toast.makeText(applicationContext, "Updated Successfully", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
    }

    //retrieve records from database
    private fun getDatabaseEntries() {
        listOfDaysBooked = arrayListOf()
        Log.d("dasdasdasda", requestID)
        var ref: DocumentReference = Util.getFirebaseFireStore().collection("carRideRequests").document(requestID.toString())
        ref.collection("Days")
            .get()
            .addOnSuccessListener {
                for (document in it.documents) {
                    var daysBooked = CarSharingDataModel(
                        document.data!!["day"].toString(),
                        document.data!!["pickUpTime"].toString(),
                        document.data!!["dropOffTime"].toString()
                    )
                    listOfDaysBooked.add(daysBooked)
                }
                previewDataFromDatabase()
            }
            .addOnFailureListener{
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
            }
    }

    //set the record values from the database
    private fun previewDataFromDatabase() {
        if (listOfDaysBooked.find { e -> e.day.equals("Monday") } != null) {
            val model = listOfDaysBooked.find { e -> e.day.equals("Monday") }
            //condition to verify that 24 hours haven't passed
            if (calendar.getDisplayName(
                    Calendar.DAY_OF_WEEK,
                    Calendar.LONG,
                    Locale.getDefault()
                ) == "Sunday"
            ) {
                if (model!!.pickUpTime!!.split(":")[0].toInt() - calendar.get(Calendar.HOUR_OF_DAY) <= 0) {
                    Monday.setOnClickListener {
                        Toast.makeText(
                            applicationContext,
                            "Can only update before 24 hours",
                            Toast.LENGTH_LONG
                        ).show()
                        Monday.chipBackgroundColor = getColorStateList(R.color.colorText)
                        Monday.setTextColor(resources.getColor(R.color.colorPrimary, null))
                    }
                }
            }
            Monday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
            Monday.setTextColor(resources.getColor(R.color.colorText, null))
            MondayCard.visibility = View.VISIBLE
            mondayTimePickUp.text = model!!.pickUpTime.toString()
            mondayTimeDropOff.text = model!!.dropOffTime.toString()
        }
        if (listOfDaysBooked.find { e -> e.day.equals("Tuesday") } != null) {
            val model = listOfDaysBooked.find { e -> e.day.equals("Tuesday") }
            //condition to verify that 24 hours haven't passed
            if (calendar.getDisplayName(
                    Calendar.DAY_OF_WEEK,
                    Calendar.LONG,
                    Locale.getDefault()
                ) == "Monday"
            ) {
                if (model!!.pickUpTime!!.split(":")[0].toInt() - calendar.get(Calendar.HOUR_OF_DAY) <= 0) {
                    Tuesday.setOnClickListener {
                        Toast.makeText(
                            applicationContext,
                            "Can only update before 24 hours",
                            Toast.LENGTH_LONG
                        ).show()
                        Tuesday.chipBackgroundColor = getColorStateList(R.color.colorText)
                        Tuesday.setTextColor(resources.getColor(R.color.colorPrimary, null))
                    }
                }
            }
            Tuesday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
            Tuesday.setTextColor(resources.getColor(R.color.colorText, null))
            TuesdayCard.visibility = View.VISIBLE
            tuesdayTimePickUp.text = model?.pickUpTime.toString()
            tuesdayTimeDropOff.text = model?.dropOffTime.toString()
        }
        if (listOfDaysBooked.find { e -> e.day.equals("Wednesday") } != null) {
            val model = listOfDaysBooked.find { e -> e.day.equals("Wednesday") }
            //condition to verify that 24 hours haven't passed
            if (calendar.getDisplayName(
                    Calendar.DAY_OF_WEEK,
                    Calendar.LONG,
                    Locale.getDefault()
                ) == "Tuesday"
            ) {
                if (model!!.pickUpTime!!.split(":")[0].toInt() - calendar.get(Calendar.HOUR_OF_DAY) <= 0) {
                    Wednesday.setOnClickListener {
                        Toast.makeText(
                            applicationContext,
                            "Can only update before 24 hours",
                            Toast.LENGTH_LONG
                        ).show()
                        Wednesday.chipBackgroundColor = getColorStateList(R.color.colorText)
                        Wednesday.setTextColor(resources.getColor(R.color.colorPrimary, null))
                    }
                }
            }
            Wednesday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
            Wednesday.setTextColor(resources.getColor(R.color.colorText, null))
            WednesdayCard.visibility = View.VISIBLE
            wednesdayTimePickUp.text = model?.pickUpTime.toString()
            wednesdayTimeDropOff.text = model?.dropOffTime.toString()
        }
        if (listOfDaysBooked.find { e -> e.day.equals("Thursday") } != null) {
            val model = listOfDaysBooked.find { e -> e.day.equals("Thursday") }
            //condition to verify that 24 hours haven't passed
            if (calendar.getDisplayName(
                    Calendar.DAY_OF_WEEK,
                    Calendar.LONG,
                    Locale.getDefault()
                ) == "Wednesday"
            ) {
                if (model!!.pickUpTime!!.split(":")[0].toInt() - calendar.get(Calendar.HOUR_OF_DAY) <= 0) {
                    Thursday.setOnClickListener {
                        Toast.makeText(
                            applicationContext,
                            "Can only update before 24 hours",
                            Toast.LENGTH_LONG
                        ).show()
                        Thursday.chipBackgroundColor = getColorStateList(R.color.colorText)
                        Thursday.setTextColor(resources.getColor(R.color.colorPrimary, null))
                    }
                }
            }
            Thursday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
            Thursday.setTextColor(resources.getColor(R.color.colorText, null))
            ThursdayCard.visibility = View.VISIBLE
            thursdayTimePickUp.text = model?.pickUpTime.toString()
            thursdayTimeDropOff.text = model?.dropOffTime.toString()
        }
        if (listOfDaysBooked.find { e -> e.day.equals("Friday") } != null) {
            val model = listOfDaysBooked.find { e -> e.day.equals("Friday") }
            if (calendar.getDisplayName(
                    Calendar.DAY_OF_WEEK,
                    Calendar.LONG,
                    Locale.getDefault()
                ) == "Thursday"
            ) {
                if (model!!.pickUpTime!!.split(":")[0].toInt() - calendar.get(Calendar.HOUR_OF_DAY) <= 0) {
                    Friday.setOnClickListener {
                        Toast.makeText(
                            applicationContext,
                            "Can only update before 24 hours",
                            Toast.LENGTH_LONG
                        ).show()
                        Friday.chipBackgroundColor = getColorStateList(R.color.colorText)
                        Friday.setTextColor(resources.getColor(R.color.colorPrimary, null))
                    }
                }
            }
            Friday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
            Friday.setTextColor(resources.getColor(R.color.colorText, null))
            FridayCard.visibility = View.VISIBLE
            fridayTimePickUp.text = model?.pickUpTime.toString()
            fridayTimeDropOff.text = model?.dropOffTime.toString()
        }
        if (listOfDaysBooked.find { e -> e.day.equals("Saturday") } != null) {
            val model = listOfDaysBooked.find { e -> e.day.equals("Saturday") }
            if (calendar.getDisplayName(
                    Calendar.DAY_OF_WEEK,
                    Calendar.LONG,
                    Locale.getDefault()
                ) == "Friday"
            ) {
                if (model!!.pickUpTime!!.split(":")[0].toInt() - calendar.get(Calendar.HOUR_OF_DAY) <= 0) {
                    Saturday.setOnClickListener {
                        Toast.makeText(
                            applicationContext,
                            "Can only update before 24 hours",
                            Toast.LENGTH_LONG
                        ).show()
                        Saturday.chipBackgroundColor = getColorStateList(R.color.colorText)
                        Saturday.setTextColor(resources.getColor(R.color.colorPrimary, null))
                    }
                }
            }
            Saturday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
            Saturday.setTextColor(resources.getColor(R.color.colorText, null))
            SaturdayCard.visibility = View.VISIBLE
            saturdayTimePickUp.text = model?.pickUpTime.toString()
            saturdayTimeDropOff.text = model?.dropOffTime.toString()
        }
        if (listOfDaysBooked.find { e -> e.day.equals("Sunday") } != null) {
            val model = listOfDaysBooked.find { e -> e.day.equals("Sunday") }
            if (calendar.getDisplayName(
                    Calendar.DAY_OF_WEEK,
                    Calendar.LONG,
                    Locale.getDefault()
                ) == "Saturday"
            ) {
                if (model!!.pickUpTime!!.split(":")[0].toInt() - calendar.get(Calendar.HOUR_OF_DAY) <= 0) {
                    Sunday.setOnClickListener {
                        Toast.makeText(
                            applicationContext,
                            "Can only update before 24 hours",
                            Toast.LENGTH_LONG
                        ).show()
                        Sunday.chipBackgroundColor = getColorStateList(R.color.colorText)
                        Sunday.setTextColor(resources.getColor(R.color.colorPrimary, null))
                    }
                }
            }
            Sunday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
            Sunday.setTextColor(resources.getColor(R.color.colorText, null))
            SundayCard.visibility = View.VISIBLE
            sundayTimePickUp.text = model?.pickUpTime.toString()
            sundayTimeDropOff.text = model?.dropOffTime.toString()

        }
        updateDaysTimeProgressBar.visibility = View.INVISIBLE
    }

    //Initilization
    private fun init() {

        //Progress Bar
        updateDaysTimeProgressBar = findViewById(R.id.updateDaysTimeProgressBar)
        updateDaysTimeProgressBar.visibility = View.VISIBLE

        requestID = intent.extras!!.get("requestID").toString()

        btnContinue = findViewById(R.id.btn_Continue)
        btnContinue.onClick {
            updateData()
        }
        //Monday
        //textTime pickup
        mondayTimePickUp = findViewById(R.id.textViewPickUpTimeMon)
        //textInfoTime pickup
        mondayPickUpTextView = findViewById(R.id.txt_pick_mon)
        mondayPickUpTextView.setOnClickListener {
            getTimerDialog(mondayTimePickUp).show()
        }

        mondayTimeDropOff = findViewById(R.id.textViewDropOffTimeMon)
        mondayDropOffTextView = findViewById(R.id.txt_drop_mon)
        mondayDropOffTextView.setOnClickListener {
            getTimerDialog(mondayTimeDropOff).show()
        }
        //card
        MondayCard = findViewById(R.id.materialCardViewMon)

        //chip
        Monday = findViewById(R.id.chip_mon)
        Monday.setOnClickListener {
            if (Monday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Monday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Monday.setTextColor(resources.getColor(R.color.colorText1, null))
                mondayTimePickUp.text = ""
                mondayTimeDropOff.text = ""
                MondayCard.visibility = View.GONE
            } else {
                Monday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Monday.setTextColor(resources.getColor(R.color.colorText, null))
                MondayCard.visibility = View.VISIBLE
            }
        }

        //Tuesday
        //textTime pickup
        tuesdayTimePickUp = findViewById(R.id.textViewPickUpTimeTue)
        //textInfoTime pickup
        tuesdayPickUpTextView = findViewById(R.id.txt_pick_tue)
        tuesdayPickUpTextView.setOnClickListener {
            getTimerDialog(tuesdayTimePickUp).show()
        }
        tuesdayTimeDropOff = findViewById(R.id.textViewDropOffTimeTue)
        tuesdayDropOffTextView = findViewById(R.id.txt_drop_tue)
        tuesdayDropOffTextView.setOnClickListener {
            getTimerDialog(tuesdayTimeDropOff).show()
        }
        //card
        TuesdayCard = findViewById(R.id.materialCardViewTue)

        //chip
        Tuesday = findViewById(R.id.chip_tue)
        Tuesday.setOnClickListener {
            if (Tuesday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Tuesday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Tuesday.setTextColor(resources.getColor(R.color.colorText1, null))
                tuesdayTimePickUp.text = ""
                tuesdayTimeDropOff.text = ""
                TuesdayCard.visibility = View.GONE
            } else {
                Tuesday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Tuesday.setTextColor(resources.getColor(R.color.colorText, null))
                TuesdayCard.visibility = View.VISIBLE
            }
        }


        //Wednesday
        //textTime pickup
        wednesdayTimePickUp = findViewById(R.id.textViewPickUpTimeWed)
        //textInfoTime pickup
        wednesdayPickUpTextView = findViewById(R.id.txt_pick_wed)
        wednesdayPickUpTextView.setOnClickListener {
            getTimerDialog(wednesdayTimePickUp).show()
        }
        wednesdayTimeDropOff = findViewById(R.id.textViewDropOffTimeWed)
        wednesdayDropOffTextView = findViewById(R.id.txt_drop_wed)
        wednesdayDropOffTextView.setOnClickListener {
            getTimerDialog(wednesdayTimeDropOff).show()
        }
        //card
        WednesdayCard = findViewById(R.id.materialCardViewWed)

        //chip
        Wednesday = findViewById(R.id.chip_wed)
        Wednesday.setOnClickListener {
            if (Wednesday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Wednesday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Wednesday.setTextColor(resources.getColor(R.color.colorText1, null))
                wednesdayTimePickUp.text = ""
                wednesdayTimeDropOff.text = ""
                WednesdayCard.visibility = View.GONE
            } else {
                Wednesday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Wednesday.setTextColor(resources.getColor(R.color.colorText, null))
                WednesdayCard.visibility = View.VISIBLE
            }
        }


        //Thursday
        //textTime pickup
        thursdayTimePickUp = findViewById(R.id.textViewPickUpTimeThur)
        //textInfoTime pickup
        thursdayPickUpTextView = findViewById(R.id.txt_pick_thur)
        thursdayPickUpTextView.setOnClickListener {
            getTimerDialog(thursdayTimePickUp).show()
        }
        thursdayTimeDropOff = findViewById(R.id.textViewDropOffTimeThur)
        thursdayDropOffTextView = findViewById(R.id.txt_drop_thur)
        thursdayDropOffTextView.setOnClickListener {
            getTimerDialog(thursdayTimeDropOff).show()
        }
        //card
        ThursdayCard = findViewById(R.id.materialCardViewThur)

        //chip
        Thursday = findViewById(R.id.chip_thur)
        Thursday.setOnClickListener {
            if (Thursday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Thursday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Thursday.setTextColor(resources.getColor(R.color.colorText1, null))
                thursdayTimePickUp.text = ""
                thursdayTimeDropOff.text = ""
                ThursdayCard.visibility = View.GONE
            } else {
                Thursday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Thursday.setTextColor(resources.getColor(R.color.colorText, null))
                ThursdayCard.visibility = View.VISIBLE
            }
        }


        //Friday
        //textTime pickup
        fridayTimePickUp = findViewById(R.id.textViewPickUpTimeFri)
        //textInfoTime pickup
        fridayPickUpTextView = findViewById(R.id.txt_pick_fri)
        fridayPickUpTextView.setOnClickListener {
            getTimerDialog(fridayTimePickUp).show()
        }
        fridayTimeDropOff = findViewById(R.id.textViewDropOffTimeFri)
        fridayDropOffTextView = findViewById(R.id.txt_drop_fri)
        fridayDropOffTextView.setOnClickListener {
            getTimerDialog(fridayTimeDropOff).show()
        }
        //card
        FridayCard = findViewById(R.id.materialCardViewFri)

        //chip
        Friday = findViewById(R.id.chip_fri)
        Friday.setOnClickListener {
            if (Friday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Friday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Friday.setTextColor(resources.getColor(R.color.colorText1, null))
                fridayTimePickUp.text = ""
                fridayTimeDropOff.text = ""
                FridayCard.visibility = View.GONE
            } else {
                Friday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Friday.setTextColor(resources.getColor(R.color.colorText, null))
                FridayCard.visibility = View.VISIBLE
            }
        }


        //Saturday
        //textTime pickup
        saturdayTimePickUp = findViewById(R.id.textViewPickUpTimeSat)
        //textInfoTime pickup
        saturdayPickUpTextView = findViewById(R.id.txt_pick_sat)
        saturdayPickUpTextView.setOnClickListener {
            getTimerDialog(saturdayTimePickUp).show()
        }
        saturdayTimeDropOff = findViewById(R.id.textViewDropOffTimeSat)
        saturdayDropOffTextView = findViewById(R.id.txt_drop_sat)
        saturdayDropOffTextView.setOnClickListener {
            getTimerDialog(saturdayTimeDropOff).show()
        }
        //card
        SaturdayCard = findViewById(R.id.materialCardViewSat)

        //chip
        Saturday = findViewById(R.id.chip_sat)
        Saturday.setOnClickListener {
            if (Saturday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Saturday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Saturday.setTextColor(resources.getColor(R.color.colorText1, null))
                saturdayPickUpTextView.text = ""
                saturdayPickUpTextView.text = ""
                SaturdayCard.visibility = View.GONE
            } else {
                Saturday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Saturday.setTextColor(resources.getColor(R.color.colorText, null))
                SaturdayCard.visibility = View.VISIBLE
            }
        }


        //Sunday
        //textTime pickup
        sundayTimePickUp = findViewById(R.id.textViewPickUpTimeSun)
        //textInfoTime pickup
        sundayPickUpTextView = findViewById(R.id.txt_pick_sun)
        sundayPickUpTextView.setOnClickListener {
            getTimerDialog(sundayTimePickUp).show()
        }
        sundayTimeDropOff = findViewById(R.id.textViewDropOffTimeSun)
        sundayDropOffTextView = findViewById(R.id.txt_drop_sun)
        sundayDropOffTextView.setOnClickListener {
            getTimerDialog(sundayTimeDropOff).show()
        }
        //card
        SundayCard = findViewById(R.id.materialCardViewSun)

        //chip
        Sunday = findViewById(R.id.chip_sun)
        Sunday.setOnClickListener {
            if (Sunday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Sunday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Sunday.setTextColor(resources.getColor(R.color.colorText1, null))
                sundayTimeDropOff.text = ""
                sundayTimePickUp.text = ""
                SundayCard.visibility = View.GONE
            } else {
                Sunday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Sunday.setTextColor(resources.getColor(R.color.colorText, null))
                SundayCard.visibility = View.VISIBLE
            }
        }

    }

    private fun getTimerDialog(textView: TextView): TimePickerDialog {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            textView.text = SimpleDateFormat("HH:mm a").format(cal.time)
        }
        return TimePickerDialog(
            this,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            false
        )
    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }
}


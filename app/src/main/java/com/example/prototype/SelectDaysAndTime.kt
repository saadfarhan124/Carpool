package com.example.prototype

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textColor
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class SelectDaysAndTime : AppCompatActivity() {

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
    private lateinit var mondayTimePickUp:TextView
    private lateinit var tuesdayTimePickUp:TextView
    private lateinit var wednesdayTimePickUp:TextView
    private lateinit var thursdayTimePickUp:TextView
    private lateinit var fridayTimePickUp:TextView
    private lateinit var saturdayTimePickUp:TextView
    private lateinit var sundayTimePickUp:TextView

    //time dropoffs
    private lateinit var mondayTimeDropOff:TextView
    private lateinit var tuesdayTimeDropOff:TextView
    private lateinit var wednesdayTimeDropOff:TextView
    private lateinit var thursdayTimeDropOff:TextView
    private lateinit var fridayTimeDropOff:TextView
    private lateinit var saturdayTimeDropOff:TextView
    private lateinit var sundayTimeDropOff:TextView

    //Button
    private lateinit var btnContinue:Button



    //time dropoffs

    override fun onCreate(savedInstanceState: Bundle?) {
//        android:textColor="@color/colorText"
//        app:chipBackgroundColor="@color/colorPrimary"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectdays)

        init()
    }

    //Insert
    private fun insertRequest(){

    }

    private fun init() {
        btnContinue = findViewById(R.id.btn_Continue)
        btnContinue.onClick {
            insertRequest()
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
        mondayDropOffTextView  = findViewById(R.id.txt_drop_mon)
        mondayDropOffTextView.setOnClickListener{
            getTimerDialog(mondayTimeDropOff).show()
        }
        //card
        MondayCard = findViewById(R.id.materialCardViewMon)

        //chip
        Monday = findViewById(R.id.chip_mon)
        Monday.setOnClickListener {
            if (Monday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Monday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Monday.setTextColor(resources.getColor(R.color.colorText1))
                mondayTimePickUp.text = ""
                mondayTimeDropOff.text = ""
                MondayCard.visibility = View.GONE
            } else {
                Monday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Monday.setTextColor(resources.getColor(R.color.colorText))
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
        tuesdayDropOffTextView  = findViewById(R.id.txt_drop_tue)
        tuesdayDropOffTextView.setOnClickListener{
            getTimerDialog(tuesdayTimeDropOff).show()
        }
        //card
        TuesdayCard = findViewById(R.id.materialCardViewTue)

        //chip
        Tuesday = findViewById(R.id.chip_tue)
        Tuesday.setOnClickListener {
            if (Tuesday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Tuesday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Tuesday.setTextColor(resources.getColor(R.color.colorText1))
                tuesdayTimePickUp.text = ""
                tuesdayTimeDropOff.text = ""
                TuesdayCard.visibility = View.GONE
            } else {
                Tuesday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Tuesday.setTextColor(resources.getColor(R.color.colorText))
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
        wednesdayDropOffTextView  = findViewById(R.id.txt_drop_wed)
        wednesdayDropOffTextView.setOnClickListener{
            getTimerDialog(wednesdayTimeDropOff).show()
        }
        //card
        WednesdayCard = findViewById(R.id.materialCardViewWed)

        //chip
        Wednesday = findViewById(R.id.chip_wed)
        Wednesday.setOnClickListener {
            if (Wednesday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Wednesday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Wednesday.setTextColor(resources.getColor(R.color.colorText1))
                wednesdayTimePickUp.text = ""
                wednesdayTimeDropOff.text = ""
                WednesdayCard.visibility = View.GONE
            } else {
                Wednesday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Wednesday.setTextColor(resources.getColor(R.color.colorText))
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
        thursdayDropOffTextView  = findViewById(R.id.txt_drop_thur)
        thursdayDropOffTextView.setOnClickListener{
            getTimerDialog(thursdayTimeDropOff).show()
        }
        //card
        ThursdayCard = findViewById(R.id.materialCardViewThur)

        //chip
        Thursday = findViewById(R.id.chip_thur)
        Thursday.setOnClickListener {
            if (Thursday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Thursday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Thursday.setTextColor(resources.getColor(R.color.colorText1))
                thursdayTimePickUp.text = ""
                thursdayTimeDropOff.text = ""
                ThursdayCard.visibility = View.GONE
            } else {
                Thursday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Thursday.setTextColor(resources.getColor(R.color.colorText))
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
        fridayDropOffTextView  = findViewById(R.id.txt_drop_fri)
        fridayDropOffTextView.setOnClickListener{
            getTimerDialog(fridayTimeDropOff).show()
        }
        //card
        FridayCard = findViewById(R.id.materialCardViewFri)

        //chip
        Friday = findViewById(R.id.chip_fri)
        Friday.setOnClickListener {
            if (Friday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Friday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Friday.setTextColor(resources.getColor(R.color.colorText1))
                fridayTimePickUp.text = ""
                fridayTimeDropOff.text = ""
                FridayCard.visibility = View.GONE
            } else {
                Friday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Friday.setTextColor(resources.getColor(R.color.colorText))
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
        saturdayDropOffTextView  = findViewById(R.id.txt_drop_sat)
        saturdayDropOffTextView.setOnClickListener{
            getTimerDialog(saturdayTimeDropOff).show()
        }
        //card
        SaturdayCard = findViewById(R.id.materialCardViewSat)

        //chip
        Saturday = findViewById(R.id.chip_sat)
        Saturday.setOnClickListener {
            if (Saturday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Saturday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Saturday.setTextColor(resources.getColor(R.color.colorText1))
                saturdayPickUpTextView.text = ""
                saturdayPickUpTextView.text = ""
                SaturdayCard.visibility = View.GONE
            } else {
                Saturday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Saturday.setTextColor(resources.getColor(R.color.colorText))
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
        sundayDropOffTextView  = findViewById(R.id.txt_drop_sun)
        sundayDropOffTextView.setOnClickListener{
            getTimerDialog(sundayTimeDropOff).show()
        }
        //card
        SundayCard = findViewById(R.id.materialCardViewSun)

        //chip
        Sunday = findViewById(R.id.chip_sun)
        Sunday.setOnClickListener {
            if (Sunday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                Sunday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                Sunday.setTextColor(resources.getColor(R.color.colorText1))
                sundayTimeDropOff.text = ""
                sundayTimePickUp.text = ""
                SundayCard.visibility = View.GONE
            } else {
                Sunday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                Sunday.setTextColor(resources.getColor(R.color.colorText))
                SundayCard.visibility = View.VISIBLE
            }
        }

    }

    private fun getTimerDialog(textView: TextView) : TimePickerDialog{
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            textView.text = SimpleDateFormat("HH:mm").format(cal.time)
        }
        return TimePickerDialog(
            this,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        )
    }
}

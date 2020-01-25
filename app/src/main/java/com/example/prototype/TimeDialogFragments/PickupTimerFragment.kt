package com.example.prototype.TimeDialogFragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.prototype.R
import org.jetbrains.anko.find
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

class PickupTimerFragment : Fragment() {
    private lateinit var root: View
    private lateinit var timePickerPickUpTime: TimePicker
    private lateinit var calendar: Calendar
    private lateinit var pickupTime: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.activity_pickup_timer, container, false)
        calendar = Calendar.getInstance()
        timePickerPickUpTime = root.findViewById(R.id.timePickerPickUpTime)
        var minute = timePickerPickUpTime.findViewById<NumberPicker>(
            Resources.getSystem().getIdentifier("minute", "id", "android")
        )
        minute.minValue = 0
        minute.maxValue = 1
        minute.displayedValues = arrayOf("0", "30")
        minute.setOnLongPressUpdateInterval(100)


        calendar.set(Calendar.HOUR_OF_DAY, timePickerPickUpTime.hour)
        calendar.set(Calendar.MINUTE, timePickerPickUpTime.minute)
        pickupTime = SimpleDateFormat("HH:mm a").format(calendar.time)

        timePickerPickUpTime.setOnTimeChangedListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            pickupTime = SimpleDateFormat("HH:mm a").format(calendar.time)
        }
        return root
    }

    fun returnPickUpTime(): String {
        return pickupTime
    }


}
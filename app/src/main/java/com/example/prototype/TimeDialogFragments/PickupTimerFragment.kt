package com.example.prototype.TimeDialogFragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.prototype.R
import java.text.SimpleDateFormat
import java.util.*

class PickupTimerFragment : Fragment() {
    private lateinit var root: View
    private lateinit var timePickerPickUpTime: TimePicker
    private lateinit var calendar: Calendar
    private lateinit var pickupTime: String
    private var destroyFlag:Boolean  = false

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
        calendar.set(Calendar.MINUTE, if(timePickerPickUpTime.minute == 1) 30 else 0)
        pickupTime = SimpleDateFormat("HH:mm a").format(calendar.time)

        timePickerPickUpTime.setOnTimeChangedListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, if(minute == 1) 30 else 0)
            pickupTime = SimpleDateFormat("HH:mm a").format(calendar.time)
        }


        return root
    }

    fun returnPickUpTime(): String {
        return pickupTime
    }




}
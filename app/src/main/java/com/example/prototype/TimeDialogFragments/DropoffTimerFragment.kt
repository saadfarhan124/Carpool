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

class DropoffTimerFragment : Fragment() {
    private lateinit var root: View
    private lateinit var timePickerDropOffTime: TimePicker
    private lateinit var calendar: Calendar
    private lateinit var dropoffTime:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.activity_dropoff_timer, container, false)
        calendar = Calendar.getInstance()
        timePickerDropOffTime = root.findViewById(R.id.timePickerDropOff)
        var minute = timePickerDropOffTime.findViewById<NumberPicker>(
            Resources.getSystem().getIdentifier("minute", "id", "android")
        )
        minute.minValue = 0
        minute.maxValue = 1
        minute.displayedValues = arrayOf("0", "30")
        minute.setOnLongPressUpdateInterval(100)

        calendar.set(Calendar.HOUR_OF_DAY, timePickerDropOffTime.hour)
        calendar.set(Calendar.MINUTE, timePickerDropOffTime.minute)
        dropoffTime = SimpleDateFormat("HH:mm a").format(calendar.time)
        timePickerDropOffTime.setOnTimeChangedListener{ _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            dropoffTime = SimpleDateFormat("HH:mm a").format(calendar.time)
        }
        return root
    }

    fun returnDropOffTime():String{
        return dropoffTime
    }

}
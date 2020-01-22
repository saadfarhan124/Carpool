package com.example.prototype.TimeDialogFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.prototype.R
import org.jetbrains.anko.find
import java.sql.Time

class PickupTimerFragment : Fragment() {
    private lateinit var root: View
    private lateinit var timePickerPickUpTime: TimePicker

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.activity_pickup_timer, container, false)
        timePickerPickUpTime = root.findViewById(R.id.timePickerPickUpTime)
        timePickerPickUpTime.setOnTimeChangedListener{ timePicker, hour, minute ->

            Toast.makeText(root.context, "$hour $minute" , Toast.LENGTH_LONG).show()
        }
        return root
    }




}
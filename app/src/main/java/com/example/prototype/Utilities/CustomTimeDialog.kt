package com.example.prototype.Utilities


import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.example.prototype.R
import com.example.prototype.TimeDialogFragments.PickupTimerFragment
import com.example.prototype.adapters.RequestSectionPageAdapter
import com.example.prototype.adapters.TimeDialogSectionPageAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import org.jetbrains.anko.onClick
import org.w3c.dom.Text


class CustomTimeDialog : DialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_custom_timedialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val requestSectionPageAdapter = TimeDialogSectionPageAdapter(context!!,childFragmentManager)
        val viewPager: ViewPager = view.findViewById(R.id.view_pagertimer)
        viewPager.adapter = requestSectionPageAdapter
        val tabs: TabLayout = view.findViewById(R.id.tabLayout)
        tabs.setupWithViewPager(viewPager)


        var btnOkay: MaterialButton = view.findViewById(R.id.btnOk)
        btnOkay.onClick {
            val pickupTimerFragment = requestSectionPageAdapter.getInstantiatedFragment(0) as PickupTimerFragment
//            Toast.makeText(context, pickupTimerFragment.getTextView(), Toast.LENGTH_LONG).show()
        }

    }
}
package com.example.prototype.Utilities



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.example.prototype.R
import com.example.prototype.TimeDialogFragments.DropoffTimerFragment
import com.example.prototype.TimeDialogFragments.PickupTimerFragment
import com.example.prototype.adapters.TimeDialogSectionPageAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import org.jetbrains.anko.onClick


class CustomTimeDialog(var textViewPickUp: TextView, var textViewDropOff: TextView,
                       var clickedChip: Chip
) :
    DialogFragment() {

    private var pickUpTimeSelected = false
    private var timeSelected = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_custom_timedialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val requestSectionPageAdapter =
            TimeDialogSectionPageAdapter(context!!, childFragmentManager)
        val viewPager: ViewPager = view.findViewById(R.id.view_pagertimer)
        viewPager.adapter = requestSectionPageAdapter
        val tabs: TabLayout = view.findViewById(R.id.tabLayout)
        tabs.setupWithViewPager(viewPager)

        var btnOkay: MaterialButton = view.findViewById(R.id.btnOk)
        btnOkay.onClick {
            if(pickUpTimeSelected){
                val pickupTimerFragment =
                    requestSectionPageAdapter.getInstantiatedFragment(0) as PickupTimerFragment
                val dropOffFragment =
                    requestSectionPageAdapter.getInstantiatedFragment(1) as DropoffTimerFragment
                textViewPickUp.text = pickupTimerFragment.returnPickUpTime()
                textViewDropOff.text = dropOffFragment.returnDropOffTime()
                timeSelected = true
                dismiss()
            }else{
                pickUpTimeSelected = true
                tabs.getTabAt(1)!!.select()
            }

        }

        var btnCancel: MaterialButton = view.findViewById(R.id.btnCancel)
        btnCancel.onClick {
            clickedChip.chipBackgroundColor = getColorStateList(view.context,R.color.chipBackgroundDisable)
            clickedChip.setTextColor(resources.getColor(R.color.colorText1))
            dismiss()
        }
    }

    override fun onDestroyView() {
        if(!timeSelected){
            clickedChip.chipBackgroundColor = getColorStateList(view!!.context,R.color.chipBackgroundDisable)
            clickedChip.setTextColor(resources.getColor(R.color.colorText1))
            textViewPickUp.text = ""
            textViewDropOff.text = ""
        }
        super.onDestroyView()
    }




}
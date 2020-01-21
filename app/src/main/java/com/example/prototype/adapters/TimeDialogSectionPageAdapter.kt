package com.example.prototype.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.prototype.TimeDialogFragments.DropoffTimerFragment
import com.example.prototype.TimeDialogFragments.PickupTimerFragment

class TimeDialogSectionPageAdapter  (private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                PickupTimerFragment()
            }
            else -> {
                return DropoffTimerFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Pickup"
            else ->{
                return "Drop off"
            }
        }
    }

}
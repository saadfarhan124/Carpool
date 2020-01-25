package com.example.prototype.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.prototype.TimeDialogFragments.DropoffTimerFragment
import com.example.prototype.TimeDialogFragments.PickupTimerFragment

class TimeDialogSectionPageAdapter  (private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private var fragments = mutableListOf<Fragment>()

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                var pickupTimerFragment = PickupTimerFragment()
                fragments.add(pickupTimerFragment)
                return pickupTimerFragment

            }
            else -> {
                var dropoffTimerFragment= DropoffTimerFragment()
                fragments.add(dropoffTimerFragment)
                return dropoffTimerFragment
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

    fun getInstantiatedFragment(position: Int): Fragment{
        return fragments[position]
    }
}
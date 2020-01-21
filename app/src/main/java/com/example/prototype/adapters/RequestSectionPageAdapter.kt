package com.example.prototype.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.prototype.RequestFragments.ActiveFragment
import com.example.prototype.RequestFragments.CancelFragment
import com.example.prototype.RequestFragments.PendingFragment

class RequestSectionPageAdapter (private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ActiveFragment()
            }
            1 ->{
                PendingFragment()
            }
            else -> {
                return CancelFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Active"
            1 -> "Pending"
            else ->{
                return "Cancel"
            }

        }
    }
}
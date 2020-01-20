package com.example.prototype.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.prototype.ShareRidesFragment.ShareHistoryFragment
import com.example.prototype.ShareRidesFragment.ShareScheduledFragment

class MyshareridesSectionPageAdapter (private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ShareScheduledFragment()
            }
            else -> {
                return ShareHistoryFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Scheduled"
            else ->{
                return "History"
            }

        }
    }
}
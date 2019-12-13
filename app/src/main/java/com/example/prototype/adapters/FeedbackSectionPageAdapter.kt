package com.example.prototype.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.prototype.ComplaintsFragment
import com.example.prototype.SuggestionFragment



class FeedbackSectionPageAdapter(private val context: Context, fm: FragmentManager) :
FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ComplaintsFragment()
            }
            else -> {
                return SuggestionFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Complaints"
            else ->{
                return "Suggestion"
            }

        }
    }
}
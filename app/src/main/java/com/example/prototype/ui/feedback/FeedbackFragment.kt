package com.example.prototype.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.prototype.R
import com.example.prototype.adapters.FeedbackSectionPageAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_feedback.*

class FeedbackFragment:Fragment() {

    private lateinit var feedbackViewModel: FeedbackViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedbackViewModel =
            ViewModelProviders.of(this).get(FeedbackViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_feedback, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        galleryViewModel.text.observe(this, Observer {
//            textView.text = it
//        })

//        val feedbackSectionPageAdapter = FeedbackSectionPageAdapter(root.context, supportFragmentManager)
//        val viewPager: ViewPager =root.findViewById(R.id.view_pager)
//        viewPager.adapter = feedbackSectionPageAdapter
//        val tabs: TabLayout = root.findViewById(R.id.feedback_tabs)
//        tabs.setupWithViewPager(viewPager)

        val feedbackSectionPageAdapter = FeedbackSectionPageAdapter(root.context,childFragmentManager)
        val viewPager: ViewPager =root.findViewById(R.id.view_pager)
        viewPager.adapter = feedbackSectionPageAdapter
        val tabs: TabLayout = root.findViewById(R.id.feedback_tabs)
        tabs.setupWithViewPager(viewPager)

        return root
    }
}
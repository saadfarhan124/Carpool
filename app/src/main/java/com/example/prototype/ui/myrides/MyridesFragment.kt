package com.example.prototype.ui.myrides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.prototype.R
import com.example.prototype.Utilities.Util
import com.example.prototype.adapters.MyridesAdapter
import com.example.prototype.adapters.MyridesSectionPageAdapter
import com.example.prototype.dataModels.MyRides
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore


class MyridesFragment : Fragment() {

    private lateinit var myRidesViewModel: MyridesViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var db: FirebaseFirestore
    private lateinit var progressBar: ProgressBar
    private lateinit var mAdapter: MyridesAdapter
    private lateinit var root:View
    private val TAG = "SeeRoutessss"




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myRidesViewModel =
            ViewModelProviders.of(this).get(MyridesViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_myrides_current, container, false)
//        progressBar = root.findViewById(R.id.myRidesProgressBar)
//        progressBar.visibility = View.VISIBLE

        val myridesSectionPageAdapter = MyridesSectionPageAdapter(root.context,childFragmentManager)
        val viewPager: ViewPager = root.findViewById(R.id.view_pagermr)
        viewPager.adapter = myridesSectionPageAdapter
        val tabs: TabLayout = root.findViewById(R.id.tabLayout)
        tabs.setupWithViewPager(viewPager)
        return root
    }
}
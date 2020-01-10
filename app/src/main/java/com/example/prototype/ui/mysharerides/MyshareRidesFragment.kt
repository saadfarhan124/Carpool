package com.example.prototype.ui.mysharerides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.adapters.MyshareRidesAdapter

class MyshareRidesFragment: Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_myshare_rides, container, false)

        mRecyclerView = root.findViewById(R.id.myridesShareRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(root.context)
        mRecyclerView.adapter = MyshareRidesAdapter()

        return root
    }

}
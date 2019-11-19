package com.example.prototype.ui.myrides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R
import com.example.prototype.adapters.MyridesAdapter
class MyridesFragment: Fragment() {
    private lateinit var myridesViewModel: MyridesViewModel
    private lateinit var mRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myridesViewModel =
            ViewModelProviders.of(this).get(MyridesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_myrides, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        galleryViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        mRecyclerView = root.findViewById(R.id.myridesRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(root.context)
        mRecyclerView.adapter = MyridesAdapter()


        return root
    }
}
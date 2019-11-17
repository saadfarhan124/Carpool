package com.example.prototype.ui.myrides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.prototype.R

class MyridesFragment: Fragment() {
    private lateinit var myridesViewModel: MyridesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myridesViewModel =
            ViewModelProviders.of(this).get(myridesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_myrides, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        galleryViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }
}
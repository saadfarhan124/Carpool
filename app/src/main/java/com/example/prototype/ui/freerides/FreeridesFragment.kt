package com.example.prototype.ui.freerides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.prototype.R

class FreeridesFragment : Fragment() {

    private lateinit var freeridesViewModel: FreeridesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        freeridesViewModel =
            ViewModelProviders.of(this).get(FreeridesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_freerides, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        galleryViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }
}
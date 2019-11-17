package com.example.prototype.ui.newroutes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.prototype.R

class NewroutesFragment:Fragment() {
    private lateinit var newroutesViewModel: NewroutesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newroutesViewModel =
            ViewModelProviders.of(this).get(NewroutesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_newroutes, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        galleryViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }
}
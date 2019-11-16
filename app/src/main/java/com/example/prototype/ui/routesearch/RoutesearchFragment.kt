package com.example.prototype.ui.routesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.prototype.R

class RoutesearchFragment : Fragment() {

    private lateinit var routesearchViewModel: RoutesearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        routesearchViewModel =
            ViewModelProviders.of(this).get(RoutesearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_routesearch, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        galleryViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }
}
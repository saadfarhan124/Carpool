package com.example.prototype.ui.packages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.R

class PackageFragment: Fragment() {
//    private lateinit var packageViewModel:PackageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        packageViewModel =
//            ViewModelProviders.of(this).get(PackageViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_package, container, false)
//        val textView: TextView = root.findViewById(R.id._text)
//        packageViewModel.text.observe(this, Observer {
//            textView.text = it
//        })

        return root
    }
}
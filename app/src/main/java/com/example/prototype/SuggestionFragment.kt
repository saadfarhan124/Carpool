package com.example.prototype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment

class SuggestionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater!!.inflate(R.layout.activity_suggestion, container, false)

        val actv: AutoCompleteTextView = root.findViewById(R.id.outline_exposed_dropdown)
        val COUNTRIES = arrayOf("Item 1", "Item 2", "Item 3", "Item 4")


        val adapter = ArrayAdapter<String>(root.context, R.layout.activity_customspiner_item, COUNTRIES)

        val editTextFilledExposedDropdown = actv
        editTextFilledExposedDropdown.setAdapter(adapter)
        return root
    }
}
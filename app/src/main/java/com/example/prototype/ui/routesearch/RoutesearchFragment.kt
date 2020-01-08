package com.example.prototype.ui.routesearch

import android.os.Bundle
import android.text.style.CharacterStyle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.prototype.R
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient

class RoutesearchFragment : Fragment() {

    private lateinit var routesearchViewModel: RoutesearchViewModel

    //Autocomplete Session token
    private lateinit var token:AutocompleteSessionToken
    private lateinit var request:FindAutocompletePredictionsRequest
    private lateinit var placesClient: PlacesClient


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        routesearchViewModel =
            ViewModelProviders.of(this).get(RoutesearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_routesearch, container, false)


        return root
    }


}
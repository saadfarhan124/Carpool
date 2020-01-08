package com.example.prototype.ui.newroutes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.prototype.R
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import org.jetbrains.anko.find

class NewroutesFragment:Fragment() {
    private lateinit var newroutesViewModel: NewroutesViewModel

    //Autocomplete Session token
    private lateinit var token: AutocompleteSessionToken
    private lateinit var request:FindAutocompletePredictionsRequest
    private lateinit var placesClient: PlacesClient

    //Widgets
    private lateinit var toAutoCompleteTextView: AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newroutesViewModel =
            ViewModelProviders.of(this).get(NewroutesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_newroutes, container, false)

        var fruits = arrayListOf("Pineapple", "Apple", "Disco")
        //Widgets
        toAutoCompleteTextView = root.findViewById(R.id.toAutoCompleteTextView)
        var adapter = ArrayAdapter<String>(root.context, android.R.layout.select_dialog_item, fruits)

        toAutoCompleteTextView.threshold = 1
        toAutoCompleteTextView.setAdapter(adapter)

        //Places API
        Places.initialize(root.context, getString(R.string.google_maps_key))
        placesClient = Places.createClient(root.context)
        request = FindAutocompletePredictionsRequest.builder()
            .setCountry("pk")
            .setTypeFilter(TypeFilter.GEOCODE)
            .setSessionToken(token)
            .setQuery("saddar")
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener {
                for(pred in it.autocompletePredictions){
                    Log.d("SAAAAD", pred.getFullText(null).toString())
                }
            }


        return root
    }
}
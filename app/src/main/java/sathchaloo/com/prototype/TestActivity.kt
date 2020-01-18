package sathchaloo.com.prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.prototype.R
import com.example.prototype.Utilities.Util

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import org.jetbrains.anko.textChangedListener
import java.io.IOException
import java.util.*

class TestActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var marker: Marker


    //Autocomplete Session token
    private lateinit var token: AutocompleteSessionToken
    private lateinit var request: FindAutocompletePredictionsRequest
    private lateinit var placesClient: PlacesClient

    //Widgets
    private lateinit var toAutoCompleteTextView: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        init()
    }

    private fun init(){
        var places = mutableMapOf<String, String>()
        var list = mutableListOf<String>()


        //Places API
        token = AutocompleteSessionToken.newInstance()
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        placesClient = Places.createClient(applicationContext)
        //Widgets
        toAutoCompleteTextView = findViewById(R.id.toAutoCompleteTextView)
//        toAutoCompleteTextView.threshold = 3

        //Text Change Listener
        toAutoCompleteTextView.textChangedListener {
            afterTextChanged {
                request = FindAutocompletePredictionsRequest.builder()
                    .setCountry("pk")
                    .setSessionToken(token)
                    .setQuery(toAutoCompleteTextView.text.toString())
                    .build()
                placesClient.findAutocompletePredictions(request)
                    .addOnSuccessListener {
                        list = mutableListOf()
                        for(pred in it.autocompletePredictions){
                            places[pred.getFullText(null).toString()] = pred.placeId
                            list.add(pred.getFullText(null).toString())
                        }
                        var adapter = ArrayAdapter<String>(applicationContext, android.R.layout.select_dialog_item, list)
                        toAutoCompleteTextView.setAdapter(adapter)
                    }
            }
        }

        toAutoCompleteTextView.setOnItemClickListener{parent,view,position,id ->
            Places.createClient(applicationContext)
                .fetchPlace(FetchPlaceRequest.newInstance(places[list[position]]!!, Arrays.asList(
                    Place.Field.ADDRESS,
                    Place.Field.LAT_LNG
                ))).addOnSuccessListener {
                    toAutoCompleteTextView.clearFocus()
                    geolocate(place = it.place)
                }

        }





    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        marker = mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    //Function to get geo location
    fun geolocate(place: Place) {
        try {
            moveCamera(
                LatLng(place.latLng!!.latitude, place.latLng!!.longitude),
                Util.getBiggerZoomValue()
            )

            addMarker(LatLng(place.latLng!!.latitude, place.latLng!!.longitude), place.address)
        } catch (e: IOException) {
            Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    //Function to move Camera
    private fun moveCamera(latLng: LatLng, zoom: Float, moveType: Int = 0) {
        when (moveType) {
            0 -> mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
            1 -> mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
        }
    }

    //Function to add markets
    private fun addMarker(latlng: LatLng, title: String?) {
        var markerOptions = MarkerOptions().position(latlng).title(title)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_yello))
        marker = mMap.addMarker(markerOptions)
    }
}

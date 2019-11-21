package com.example.prototype

import android.content.Intent
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.array
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.maps.android.PolyUtil
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class SelectPickUpActivity : AppCompatActivity(), OnMapReadyCallback {

    //Utility Vars
    private var DEFAULT_ZOOM = 15f
    private lateinit var mMap: GoogleMap
    private lateinit var placesClient: PlacesClient
    private lateinit var autocompleteSupportFragment: AutocompleteSupportFragment
    private lateinit var marker: Marker
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var destinationLatLng: LatLng
    private lateinit var polylineOptions: PolylineOptions
    private var polyFlag = false


    //Widgets
    private var mGPS: ImageView? = null
    private var customMarker: ImageView? = null
    private var btnSelectPickUp: Button? = null
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_pick_up)
        initMap()
        mGPS = findViewById(R.id.ic_gps)
        customMarker = findViewById(R.id.ic_marker)
        btnSelectPickUp = findViewById(R.id.btnSelectPickUp)
        //Places API
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        placesClient = Places.createClient(this)
        autocompleteSupportFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteSupportFragment.setPlaceFields(arrayListOf(Place.Field.ADDRESS, Place.Field.LAT_LNG))

        destinationLatLng = LatLng(intent.extras!!.get("PickupLat").toString().toDouble(),intent.extras!!.get("PickupLong").toString().toDouble())
    }

    private fun initMap(){
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun init() {

        mGPS!!.setOnClickListener {
            getDevicesLocation()
        }

        autocompleteSupportFragment.setHint("Enter Destination")
        autocompleteSupportFragment.setCountry("PK")
        autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(p0: Status) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onPlaceSelected(p0: Place) {
                Toast.makeText(applicationContext, p0.address, Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    autocompleteSupportFragment.setText(p0.address)
                    geolocate(p0)
                }, 500)
                autocompleteSupportFragment.setText(p0.address)
            }


        })

        btnSelectPickUp!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (counter == 0) {
                    polylineOptions = PolylineOptions()
                    polylineOptions.color(Color.RED)
                    polylineOptions.width(5f)
                    val url = getURL(
                        destinationLatLng,
                        LatLng(marker.position.latitude, marker.position.longitude)
                    )
                    async {
                        val result = URL(url).readText()
                        uiThread {
                            val response = JSONObject(result)
                            val routes: JSONArray = response.getJSONArray("routes")
                            val routesObject = routes.getJSONObject(0)
                            val polylines = routesObject.getJSONObject("overview_polyline")
                            val encodedString = polylines.getString("points")
                            val bounds = LatLngBounds.Builder().include(
                                LatLng(
                                    marker.position.latitude,
                                    marker.position.longitude
                                )
                            ).include(destinationLatLng)
                            polyFlag = true
                            mMap.animateCamera(
                                CameraUpdateFactory.newLatLngBounds(
                                    bounds.build(),
                                    50
                                )
                            )
                            mMap.addPolyline(
                                PolylineOptions().addAll(PolyUtil.decode(encodedString)).color(
                                    Color.BLUE
                                )
                            )
                        }
                    }
                    counter++
                } else {
                    val intent = Intent(applicationContext, SeeRoutesActivity::class.java)
                    startActivity(intent)
                }

            }
        })

    }

    private fun getURL(from : LatLng, to : LatLng) : String {
        val origin = "origin=" + from.latitude + "," + from.longitude
        val dest = "destination=" + to.latitude + "," + to.longitude
        val api_key = "key=" + getString(R.string.google_maps_key)
        val params = "$origin&$dest&$api_key"
        return "https://maps.googleapis.com/maps/api/directions/json?$params"
    }

    //Function to get location
    private fun getDevicesLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        try{
            var task: Task<Location>? = fusedLocationProviderClient.lastLocation
            task!!.addOnCompleteListener{
                if(task.isComplete){
                    val location = task.result
                    moveCamera(LatLng(location!!.latitude,location.longitude), DEFAULT_ZOOM)
                    init()
                }else{
                    Log.d("Maps Activity", "Location Not Found")
                    Toast.makeText(applicationContext, "Not Found", Toast.LENGTH_LONG).show()
                }
            }
        }catch (e : SecurityException){
            Log.e("MapActivity",e.message.toString())
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnCameraIdleListener {
            if(!polyFlag){
                mMap.clear()
                addMarker(destinationLatLng,"Destination")
                addMarker(mMap.cameraPosition.target, "Pickup")
                customMarker!!.visibility = View.INVISIBLE
            }
        }
        mMap.setOnCameraMoveListener {
            if(!polyFlag){
                customMarker!!.visibility = View.VISIBLE
            }

        }
        getDevicesLocation()
    }

    //Function to add markets
    private fun addMarker(latlng: LatLng, title:String?){
        val markerOptions = MarkerOptions().position(latlng).title(title)
        marker = mMap.addMarker(markerOptions)
    }

    //Function to move Camera
    private fun moveCamera(latLng: LatLng, zoom:Float){
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    fun geolocate(place: Place){
        try {
            moveCamera(LatLng(place.latLng!!.latitude, place.latLng!!.longitude), DEFAULT_ZOOM)
            addMarker(LatLng(place.latLng!!.latitude, place.latLng!!.longitude), place.address)
        }catch (e: IOException){
            Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}

package com.example.prototype.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.prototype.R
import com.example.prototype.SelectPickUpActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.IOException


class HomeFragment : Fragment(), OnMapReadyCallback {


    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mMap: GoogleMap

    //Permission Vars
    private val FINE_LOCATION:String = Manifest.permission.ACCESS_FINE_LOCATION
    private val COARSE_LOCATION:String = Manifest.permission.ACCESS_COARSE_LOCATION

    //Utility Vars
    private var DEFAULT_ZOOM = 17f
    private var permissionFlag = false
    private val locationPermissionCode = 1234
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient
    private lateinit var autocompleteSupportFragment: AutocompleteSupportFragment
    private lateinit var marker: Marker
    private lateinit var root:View

    //Widgets
    private var mGPS: ImageView? = null
    private var customMarker: ImageView? = null
    private var btnSelectPickUp:Button? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_home, container, false)

        mGPS = root.findViewById(R.id.ic_gps)
        customMarker = root.findViewById(R.id.ic_marker)
        btnSelectPickUp = root.findViewById(R.id.btnSelectPickUp)
        //Places API
        Places.initialize(root.context, getString(R.string.google_maps_key))
        placesClient = Places.createClient(root.context)
        autocompleteSupportFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteSupportFragment.setPlaceFields(arrayListOf(Place.Field.ADDRESS, Place.Field.LAT_LNG))


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        getLocationPermission()

        return root
    }

    //Fifth
    //Setting Editor On Action Listener for the Enter Key
    private fun init(){
        btn_service.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.activity_services_bottomsheat, null)
            val dialog = BottomSheetDialog(root.context)
            dialog.setContentView(view)
            dialog.show()
        }
        mGPS!!.setOnClickListener {
            getDevicesLocation()
        }

        autocompleteSupportFragment.setHint("Enter Destination")
        autocompleteSupportFragment.setCountry("PK")
        autocompleteSupportFragment.setOnPlaceSelectedListener(object: PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                Handler().postDelayed({
                    autocompleteSupportFragment.setText(p0.address)
                    geolocate(p0)
                }, 500)
                autocompleteSupportFragment.setText(p0.address)
            }

            override fun onError(p0: Status) {

            }
        })

        btnSelectPickUp!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                var intent = Intent(root.context, SelectPickUpActivity::class.java)
                intent.putExtra("DestLat", marker.position.latitude)
                intent.putExtra("DestLong", marker.position.longitude)
                startActivity(intent)
            }
        })
    }

    //Function to get geo location
    fun geolocate(place: Place){
        try {
            moveCamera(LatLng(place.latLng!!.latitude, place.latLng!!.longitude), DEFAULT_ZOOM)

            addMarker(LatLng(place.latLng!!.latitude, place.latLng!!.longitude), place.address)
        }catch (e: IOException){
            Toast.makeText(root.context,e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    //Second
    //Initilizing MAp
    private fun initMap(){
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    //Third
    //Map on Ready Callback
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnCameraIdleListener {
            mMap.clear()
            addMarker(mMap.cameraPosition.target, "Custom")
            customMarker!!.visibility = View.INVISIBLE
        }
        mMap.setOnCameraMoveListener {
            customMarker!!.visibility = View.VISIBLE
        }

        if(permissionFlag){
            getDevicesLocation()
            try{
                mMap.isMyLocationEnabled = true
            }catch (e:SecurityException){

            }

        }
    }

    //Fourth
    //Function to get location
    private fun getDevicesLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
        try{
            var task: Task<Location>? = fusedLocationProviderClient.lastLocation
            task!!.addOnCompleteListener{
                if(task.isComplete){
                    var location = task.result
                    moveCamera(LatLng(location!!.latitude,location.longitude), DEFAULT_ZOOM)
                    Toast.makeText(root.context, "Found", Toast.LENGTH_LONG).show()
                    init()
                }else{
                    Log.d("Maps Activity", "Location Not Found")
                    Toast.makeText(root.context, "Not Found", Toast.LENGTH_LONG).show()
                }
            }
        }catch (e : SecurityException){
            Log.e("MapActivity",e.message.toString())
        }
    }

    //Function to move Camera
    private fun moveCamera(latLng: LatLng, zoom:Float){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    //Function to add markets
    private fun addMarker(latlng:LatLng, title:String?){
        var markerOptions = MarkerOptions().position(latlng).title(title)
        marker = mMap.addMarker(markerOptions)
    }

    //First
    //Function to get permission
    private fun getLocationPermission(){
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        if(ContextCompat.checkSelfPermission(root.context,FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(root.context,COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                permissionFlag = true
                initMap()
            }else{
                ActivityCompat.requestPermissions(activity!!, permissions, locationPermissionCode)
            }
        }else{
            ActivityCompat.requestPermissions(activity!!, permissions, locationPermissionCode)
        }
    }

    //Permission result callback
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            locationPermissionCode -> if(grantResults.isNotEmpty()){
                for(i in 0 until grantResults.size - 1 ){
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        permissionFlag = false
                        return
                    }
                }
                permissionFlag = true
                initMap()
            }
        }
    }
}
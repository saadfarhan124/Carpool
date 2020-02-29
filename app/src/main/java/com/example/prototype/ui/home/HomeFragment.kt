package com.example.prototype.ui.home

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.prototype.R
import com.example.prototype.SelectPickUpActivity
import com.example.prototype.Utilities.Util
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import org.jetbrains.anko.async
import org.jetbrains.anko.onClick
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import kotlin.system.exitProcess


class HomeFragment : Fragment(), OnMapReadyCallback, LocationListener {

    //Location callbacks
    override fun onLocationChanged(location: Location?) {

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
        val alertDialog = Util.getAlertDialog(root.context)
        alertDialog.setMessage("Location need to be opened to use this application. Would you like to proceed?")
        alertDialog.setPositiveButton("Ok") { _, _ ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
        alertDialog.setNegativeButton("No") { _, _ ->
            activity!!.finishAffinity()
            exitProcess(0)
        }
    }


    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mMap: GoogleMap

    //Permission Vars
    private val fineLocation: String = Manifest.permission.ACCESS_FINE_LOCATION
    private val coarseLocation: String = Manifest.permission.ACCESS_COARSE_LOCATION

    //Utility Vars
    private var permissionFlag = false
    private val locationPermissionCode = 1234
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient
    private lateinit var autocompleteSupportFragment: AutocompleteSupportFragment
    private lateinit var marker: Marker
    private lateinit var root: View
    private lateinit var locationManager: LocationManager


    //Widgets
    private lateinit var btnService: Button
    private lateinit var mGPS: ImageView
    private var customMarker: ImageView? = null
    private lateinit var btnSelectPickUp: Button

    //Destinatiion Address
    private var destAddress = ""




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_home, container, false)


        //Location Manager
        locationManager = root.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager


        if (Util.verifyAvailableNetwork(activity!! as AppCompatActivity)) {
            if (Util.isGPSEnable(locationManager)) {
                try {
                    getLocationPermission()

                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        15000,
                        10f,
                        this
                    )

                } catch (e: SecurityException) {
                    Toast.makeText(root.context, e.message, Toast.LENGTH_SHORT).show()
                }
            } else {
                val alertDialog = Util.getAlertDialog(root.context)
                alertDialog.setMessage("Location need to be opened to use this application. Would you like to proceed?")
                alertDialog.setPositiveButton("Ok") { _, _ ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
                alertDialog.setNegativeButton("No") { _, _ ->
                    activity!!.finishAndRemoveTask()
                    exitProcess(0)
                }
                alertDialog.show()

            }

        } else {
            val confirmDialog =
                AlertDialog.Builder(root.context, R.style.ThemeOverlay_MaterialComponents_Dialog)
            confirmDialog.setTitle("Sath Chaloo")
            confirmDialog.setMessage("Please connect to internet")
            confirmDialog.setPositiveButton("Ok") { _, _ ->
                activity!!.finishAffinity()
                exitProcess(0)
            }
            confirmDialog.show()
        }
        return root
    }

    //Fifth
    //Setting Editor On Action Listener for the Enter Key
    private fun init() {

        mGPS = root.findViewById(R.id.ic_gps)
        customMarker = root.findViewById(R.id.ic_marker)

        btnSelectPickUp = root.findViewById(R.id.btnSelectPickUp)

        //Places API
        Places.initialize(root.context, getString(R.string.google_maps_key))
        placesClient = Places.createClient(root.context)
        autocompleteSupportFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        root.findViewById<EditText>(R.id.places_autocomplete_search_input).textSize = 15f
        autocompleteSupportFragment.setPlaceFields(
            arrayListOf(
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        )



        btnService = root.findViewById(R.id.btn_service)
        btnService.setOnClickListener {
            val dialog = BottomSheetDialog(root.context)
            val view = layoutInflater.inflate(R.layout.activity_services_bottomsheat, null)
            //Services On Click
            view.findViewById<MaterialCardView>(R.id.materialCardViewbus).onClick {
                Toast.makeText(root.context, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            view.findViewById<MaterialCardView>(R.id.materialCardViewcar).onClick {
                Toast.makeText(root.context, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            view.findViewById<MaterialCardView>(R.id.materialCardViewcarpool).onClick {
                dialog.dismiss()
            }
            dialog.setContentView(view)
            dialog.show()
        }
        mGPS.setOnClickListener {
            getDevicesLocation()
        }


        autocompleteSupportFragment.setHint("Enter Destination")
        autocompleteSupportFragment.setCountry("PK")
        autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                Handler().postDelayed({
                    autocompleteSupportFragment.setText(p0.address)
                    geolocate(p0)
                    destAddress = p0.address!!
                }, 500)
            }

            override fun onError(p0: Status) {

            }
        })

        btnSelectPickUp.onClick {
            if (::marker.isInitialized) {
                val intent = Intent(root.context, SelectPickUpActivity::class.java)
                intent.putExtra("DestAddress", destAddress)
                intent.putExtra("DestLat", marker.position.latitude)
                intent.putExtra("DestLong", marker.position.longitude)
                startActivity(intent)
            }
        }
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
            Toast.makeText(root.context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    //Second
    //Initilizing MAp
    private fun initMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    //Third
    //Map on Ready Callback
    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(root.context)
        mMap = googleMap
        mMap.setOnCameraIdleListener {
            mMap.clear()
            val url = Util.getGeoCodeUrl(mMap.cameraPosition.target.latitude, mMap.cameraPosition.target.longitude, getString(R.string.google_maps_key))
            async {
                val result = URL(url).readText()
                uiThread {
                    val response = JSONObject(result)
                    val routes: JSONArray = response.getJSONArray("results")
                    //bug
                    destAddress = routes.getJSONObject(0).getString("formatted_address")
                    autocompleteSupportFragment.setText(destAddress)
                    customMarker!!.visibility = View.INVISIBLE
                    addMarker(mMap.cameraPosition.target, "Custom")
                }
            }
        }
        mMap.setOnCameraMoveListener {
            customMarker!!.visibility = View.VISIBLE
        }

        btnSelectPickUp.visibility = View.VISIBLE

        if (permissionFlag) {
            getDevicesLocation()
        }
    }

    //Fourth
    //Function to get location
    private fun getDevicesLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(root.context)
        try {
            val task: Task<Location>? = fusedLocationProviderClient.lastLocation
            task!!.addOnCompleteListener {
                if (task.isSuccessful) {
                    val location = task.result
                    if(location != null){
                        moveCamera(
                            LatLng(location.latitude, location.longitude),
                            Util.getBiggerZoomValue()
                        )
                    }else{
                        val locationCallback = object : LocationCallback(){
                            override fun onLocationResult(p0: LocationResult) {
                                moveCamera(LatLng(p0.lastLocation.latitude, p0.lastLocation.longitude),
                                    Util.getBiggerZoomValue())
                            }
                        }
                        val locationRequest = LocationRequest()
                        locationRequest.priority = (LocationRequest.PRIORITY_HIGH_ACCURACY)
                        locationRequest.interval = (0)
                        locationRequest.fastestInterval = (0)
                        locationRequest.numUpdates = (1)
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(root.context)
                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest, locationCallback, Looper.myLooper()
                        )
                    }
                } else {
                    Toast.makeText(root.context, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: SecurityException) {
            Log.e("MapActivity", e.message.toString())
        }
    }

    //Function to move Camera
    private fun moveCamera(latLng: LatLng, zoom: Float) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    //Function to add markets
    private fun addMarker(latlng: LatLng, title: String?) {
        val markerOptions = MarkerOptions().position(latlng).title(title)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_yello))
        marker = mMap.addMarker(markerOptions)
    }

    //First
    //Function to get permission
    private fun getLocationPermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (ContextCompat.checkSelfPermission(
                root.context,
                fineLocation
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                    root.context,
                    coarseLocation
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                init()
                initMap()
                getDevicesLocation()
                permissionFlag = true
            } else {
                requestPermissions(permissions, locationPermissionCode)
            }
        } else {
            requestPermissions(permissions, locationPermissionCode)
        }
    }

    //Permission result callback
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            locationPermissionCode -> if (grantResults.isNotEmpty()) {
                for (i in 0 until grantResults.size - 1) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        val alertDialog = Util.getAlertDialog(root.context)
                        alertDialog.setMessage("You need to enable location in order to get the most out of Sath Chaloo")
                        alertDialog.setPositiveButton("Enable Location") { _, _ ->
                            getLocationPermission()
                        }
                        alertDialog.setNegativeButton("Don't allow") { _, _ ->
                            exitProcess(0)
                        }
                        alertDialog.show()
                        permissionFlag = false
                    }
                }
                permissionFlag = true
                init()
                initMap()
                getDevicesLocation()
            }
        }
    }
}
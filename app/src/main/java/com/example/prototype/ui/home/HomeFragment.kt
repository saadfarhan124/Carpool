package com.example.prototype.ui.home

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.prototype.R
import com.example.prototype.SelectPickUpActivity
import com.example.prototype.Utilities.Util
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.find
import java.io.IOException
import java.lang.StringBuilder
import java.util.*


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
            activity!!.finishAffinity();
            System.exit(0)
        }
    }


    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mMap: GoogleMap

    //Permission Vars
    private val FINE_LOCATION: String = Manifest.permission.ACCESS_FINE_LOCATION
    private val COARSE_LOCATION: String = Manifest.permission.ACCESS_COARSE_LOCATION

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
    private lateinit var btn_service: Button
    private lateinit var mGPS: ImageView
    private var customMarker: ImageView? = null
    private var btnSelectPickUp: Button? = null

    //Destinatiion Address
    private var destAddress = ""

    //GeoCoder
    private lateinit var geocoder: Geocoder

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
                    System.exit(0)
                }
                alertDialog.show()

            }

        }else{
            val confirmDialog =
                AlertDialog.Builder(root.context, R.style.ThemeOverlay_MaterialComponents_Dialog)
            confirmDialog.setTitle("Sath Chaloo")
            confirmDialog.setMessage("Please connect to internet")
            confirmDialog.setPositiveButton("Ok") { _, _ ->
                activity!!.finishAffinity();
                System.exit(0)
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
        geocoder = Geocoder(root.context, Locale.getDefault())


        btn_service = root.findViewById(R.id.btn_service)
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

        btnSelectPickUp!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var intent = Intent(root.context, SelectPickUpActivity::class.java)
                intent.putExtra("DestAddress", destAddress)
                intent.putExtra("DestLat", marker.position.latitude)
                intent.putExtra("DestLong", marker.position.longitude)
                startActivity(intent)
            }
        })
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
            var address = geocoder.getFromLocation(mMap.cameraPosition.target.latitude,mMap.cameraPosition.target.longitude, 1)
            destAddress = address.first().getAddressLine(0).toString()
            autocompleteSupportFragment.setText(address.first().getAddressLine(0).toString())
            addMarker(mMap.cameraPosition.target, "Custom")
            customMarker!!.visibility = View.INVISIBLE
        }
        mMap.setOnCameraMoveListener {
            customMarker!!.visibility = View.VISIBLE
        }

        if (permissionFlag) {
            getDevicesLocation()
        }
    }

    //Fourth
    //Function to get location
    private fun getDevicesLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
        try {
            var task: Task<Location>? = fusedLocationProviderClient.lastLocation
            task!!.addOnCompleteListener {
                if (task.isSuccessful) {
                    var location = task.result
                    moveCamera(
                        LatLng(location!!.latitude, location.longitude),
                        Util.getBiggerZoomValue(),
                        1
                    )
                    Toast.makeText(root.context, "Found", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(root.context, "Not Found", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: SecurityException) {
            Log.e("MapActivity", e.message.toString())
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

    //First
    //Function to get permission
    private fun getLocationPermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (ContextCompat.checkSelfPermission(
                root.context,
                FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                    root.context,
                    COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                init()
                initMap()
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
                        var alertDialog = Util.getAlertDialog(root.context)
                        alertDialog.setMessage("You need to enable location in order to get the most out of Sath Chaloo")
                        alertDialog.setPositiveButton("Enable Location"){_,_ ->
                            getLocationPermission()
                        }
                        alertDialog.setNegativeButton("Don't allow"){_,_ ->
                            System.exit(0)
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
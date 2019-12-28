package com.example.prototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.prototype.Utilities.Util

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint

class TrackRideActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var docRefDriverLocation : DocumentReference
    private lateinit var driverLatLng: LatLng
    private lateinit var markerOptions: MarkerOptions

    private var TAG = "TrackRideeee"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_ride)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val toolbar: Toolbar = findViewById(R.id.toolbarTrackRide)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        initilize()
        // Add a marker in Sydney and move the camera

    }



    private fun initilize(){
        Util.getFirebaseFireStore().collection("driver_routes")
            .whereEqualTo("routeId", intent.extras!!.get("routeId"))
            .get()
            .addOnSuccessListener {
                if(it.first()["rideStatus"] == "unbegun"){
                    Toast.makeText(applicationContext, "Ride hasn't commenced yet", Toast.LENGTH_LONG).show()
                }else{
                    docRefDriverLocation = it.first().reference
                    docRefDriverLocation.addSnapshotListener{ snapshot, e ->
                        if(snapshot != null && snapshot.exists()){
                            mMap.clear()
                            var geoPoint = snapshot["driver_location"] as GeoPoint
                            driverLatLng = LatLng(geoPoint.latitude, geoPoint.longitude)
                            markerOptions = MarkerOptions().position(driverLatLng).title("Driver")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_yello))
                            mMap.addMarker(markerOptions)
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(driverLatLng, Util.getBiggerZoomValue()))
                        }

                    }
                }
            }
            .addOnFailureListener{

            }
    }
}

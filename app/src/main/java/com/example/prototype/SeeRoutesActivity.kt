package com.example.prototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prototype.Utilities.Util

import com.example.prototype.adapters.RoutesAdapter
import com.example.prototype.dataModels.Routes
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot

class SeeRoutesActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var db: FirebaseFirestore
    private lateinit var progressBar: ProgressBar
    private val TAG = "SeeRoutessss"
    private var mAdapter: RoutesAdapter? = null

    private lateinit var pickUpLatLng: LatLng
    private lateinit var destLatLng: LatLng


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeroutes)
        db = FirebaseFirestore.getInstance()

        val toolbar: Toolbar = findViewById(R.id.toolbarsr)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //progress bar
        progressBar = findViewById(R.id.routesProgressBar)
        progressBar.visibility = View.VISIBLE

        init()

        loadRoutes()

    }

    private fun init() {
        destLatLng = LatLng(
            intent.extras!!.get("DestLat").toString().toDouble(),
            intent.extras!!.get("DestLong").toString().toDouble()
        )
        pickUpLatLng = LatLng(
            intent.extras!!.get("PickupLat").toString().toDouble(),
            intent.extras!!.get("PickupLong").toString().toDouble()
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun loadRoutes() {

        db.collection("routes")
            .whereGreaterThan("seatsRemaining", 0)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val routes = mutableListOf<Routes>()
                    for (document in task.result!!) {
                        //accessing sub collection
                        document.reference.collection("stops").get()
                            .addOnCompleteListener { taskTwo ->
                                if (taskTwo.isSuccessful) {

                                    //flags to make sure that the route has stops both near the pick up and destination
                                    var pickUpFlag = false
                                    var destFlag = false


                                    for (doc in taskTwo.result!!) {
                                        var geoPoint = doc["stop_latlng"] as GeoPoint
                                        if(Util.getDistance(pickUpLatLng, LatLng(geoPoint.latitude, geoPoint.longitude)) < 800){
                                            pickUpFlag = true
                                            Util.getGlobals().pickUpSpot = LatLng(geoPoint.latitude, geoPoint.longitude)
                                            Util.getGlobals().distanceFromPickUp = Util.getDistance(pickUpLatLng, LatLng(geoPoint.latitude, geoPoint.longitude))
                                        }else if(Util.getDistance(destLatLng, LatLng(geoPoint.latitude, geoPoint.longitude)) < 800){
                                            destFlag = true
                                            Util.getGlobals().dropOffSpot = LatLng(geoPoint.latitude, geoPoint.longitude)
                                            Util.getGlobals().distanceFromDropOff = Util.getDistance(destLatLng, LatLng(geoPoint.latitude, geoPoint.longitude))
                                        }
                                    }
                                    if(pickUpFlag && destFlag){
                                        val route = document.toObject(Routes::class.java)
                                        route.distanceFromPickUp = Util.getGlobals().distanceFromPickUp
                                        route.distanceFromDropOff = Util.getGlobals().distanceFromDropOff
                                        route.id = document.id
                                        routes.add(route)
                                    }
                                    mAdapter = RoutesAdapter(routes, applicationContext, db!!)
                                    mRecyclerView = findViewById(R.id.recyclerViewRoutes)
                                    val mLayoutManager = LinearLayoutManager(applicationContext)
                                    mRecyclerView.layoutManager = mLayoutManager
                                    mRecyclerView.itemAnimator = DefaultItemAnimator()
                                    mRecyclerView.adapter = mAdapter
                                    //progress bar
                                    progressBar.visibility = View.INVISIBLE
                                } else {
                                    Log.d(TAG, taskTwo.exception.toString())
                                }
                            }
                    }

                } else {
                    Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
    }
}

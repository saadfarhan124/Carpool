package com.example.prototype

import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.prototype.Utilities.Util
import com.example.prototype.dataModels.CarSharingDataModel
import com.example.prototype.dataModels.DaysDataModel
import com.example.prototype.dataModels.ReviewInformationDataModel
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.maps.android.PolyUtil
import org.jetbrains.anko.async
import org.jetbrains.anko.onClick
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.Serializable
import java.net.URL
import java.util.*

class SelectPickUpActivity : AppCompatActivity(), OnMapReadyCallback {

    //Utility Vars
    private lateinit var mMap: GoogleMap
    private lateinit var placesClient: PlacesClient
    private lateinit var autocompleteSupportFragment: AutocompleteSupportFragment
    private lateinit var marker: Marker
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var destinationLatLng: LatLng
    private lateinit var destinationAddress: String
    private lateinit var pickUpAddress: String
    private lateinit var polylineOptions: PolylineOptions
    private var polyFlag = false


    //Widgets
    private var mGPS: ImageView? = null
    private var customMarker: ImageView? = null
    private var btnSelectPickUp: Button? = null
    private var counter = 0


    //Chips
    private lateinit var Monday: Chip
    private lateinit var Tuesday: Chip
    private lateinit var Wednesday: Chip
    private lateinit var Thursday: Chip
    private lateinit var Friday: Chip
    private lateinit var Saturday: Chip
    private lateinit var Sunday: Chip

    //time pickup
    private lateinit var textViewMondayPickup:TextView
    private lateinit var textViewTuesdayPickup:TextView
    private lateinit var textViewWednesdayPickup:TextView
    private lateinit var textViewThursdayPickup:TextView
    private lateinit var textViewFridayPickup:TextView
    private lateinit var textViewSaturdayPickup:TextView
    private lateinit var textViewSundayPickup:TextView

    //time dropoffs
    private lateinit var textViewMondayDropoff:TextView
    private lateinit var textViewTuesdayDropoff:TextView
    private lateinit var textViewWednesdayDropoff:TextView
    private lateinit var textViewThursdayDropoff:TextView
    private lateinit var textViewFridayDropoff:TextView
    private lateinit var textViewSaturdayDropoff:TextView
    private lateinit var textViewSundayDropoff:TextView


    //Days datamodel
    private lateinit var daysDataModel: DaysDataModel

    //Review data model
    private lateinit var reviewInfo: ReviewInformationDataModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_pick_up)
        initMap()
        mGPS = findViewById(R.id.ic_gps)
        customMarker = findViewById(R.id.ic_marker)
        btnSelectPickUp = findViewById(R.id.btnSelectPickUp)

        //Places API
        autocompleteSupportFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        findViewById<EditText>(R.id.places_autocomplete_search_input).textSize = 15f
        autocompleteSupportFragment.setPlaceFields(arrayListOf(Place.Field.ADDRESS, Place.Field.LAT_LNG))

        destinationLatLng = LatLng(intent.extras!!.get("DestLat").toString().toDouble(),intent.extras!!.get("DestLong").toString().toDouble())
        destinationAddress = intent.extras!!.getString("DestAddress").toString()
        //Top App Bar
        val toolbar: Toolbar = findViewById(R.id.toolbarsp)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        
    }

    //Top App Bar Back Nav
    override fun onSupportNavigateUp():Boolean {
        onBackPressed()
        return true
    }

    private fun initMap(){
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun init() {

        daysDataModel = DaysDataModel()

        mGPS!!.setOnClickListener {
            getDevicesLocation()
        }

        autocompleteSupportFragment.setHint("Enter Pickup:")
        autocompleteSupportFragment.setCountry("PK")
        autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(p0: Status) {

            }

            override fun onPlaceSelected(p0: Place) {
                Handler().postDelayed({
                    pickUpAddress = p0.address!!
                    autocompleteSupportFragment.setText(p0.address)
                    geolocate(p0)
                }, 500)
                autocompleteSupportFragment.setText(p0.address)
            }


        })

        btnSelectPickUp!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(::marker.isInitialized){
                    if (counter == 0) {
                        polylineOptions = PolylineOptions()
                        polylineOptions.color(Color.RED)
                        polylineOptions.width(5f)
                        val url = Util.getURL(
                            LatLng(marker.position.latitude, marker.position.longitude),
                            destinationLatLng,
                            getString(R.string.google_maps_key)
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
                                        100
                                    )
                                )
                                mMap.addPolyline(
                                    PolylineOptions().addAll(PolyUtil.decode(encodedString)).color(
                                        Color.BLUE
                                    ))

                            }
                        }
                        counter++
                        btnSelectPickUp!!.text = "Proceed"
                    } else {

                        //Old Select Days and time
//                    val intent = Intent(applicationContext, SelectDaysAndTime::class.java)
//                    intent.putExtra("DestLat", destinationLatLng.latitude)
//                    intent.putExtra("DestLong", destinationLatLng.longitude)
//                    intent.putExtra("DestAddress", destinationAddress)
//                    intent.putExtra("PickupLat", marker.position.latitude)
//                    intent.putExtra("PickupLong", marker.position.longitude)
//                    intent.putExtra("PickUpAddress", pickUpAddress)
//                    startActivity(intent)
                        //Bottom Sheet
                        val view =
                            layoutInflater.inflate(R.layout.activity_selectdays_bottomsheet, null)
                        val dialog = BottomSheetDialog(this@SelectPickUpActivity)
                        dialog.setContentView(view)
                        dialog.show()

                        //Monday
                        //Chips
                        Monday = view.findViewById(R.id.chip_mon)
                        //Pick Ups Text View
                        textViewMondayPickup = view.findViewById(R.id.textViewMondayPickup)
                        textViewMondayDropoff = view.findViewById(R.id.textViewMondayDropoff)
                        //OnClick Chips
                        Monday.onClick {
                            if (Monday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                                Monday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                                Monday.setTextColor(resources.getColor(R.color.colorText1))
                            }else{
                                Monday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                                Monday.setTextColor(resources.getColor(R.color.colorText))
                                val dialogFragment = Util.getCustomTimeDialog(textViewMondayPickup, textViewMondayDropoff, Monday)
                                val sf = supportFragmentManager.beginTransaction()
                                sf.addToBackStack(null)
                                dialogFragment.show(sf, "dialog")
                            }
                        }


                        //Tuesday
                        //Chip
                        Tuesday = view.findViewById(R.id.chip_tue)
                        //Pick Ups Text View
                        textViewTuesdayPickup = view.findViewById(R.id.textViewTuesdayPickup)
                        textViewTuesdayDropoff = view.findViewById(R.id.textViewTuesdayDropoff)
                        //OnClick Chips
                        Tuesday.onClick {
                            if (Tuesday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                                Tuesday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                                Tuesday.setTextColor(resources.getColor(R.color.colorText1))
                            }else{
                                Tuesday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                                Tuesday.setTextColor(resources.getColor(R.color.colorText))
                                val dialogFragment = Util.getCustomTimeDialog(textViewTuesdayPickup, textViewTuesdayDropoff, Tuesday)
                                val sf = supportFragmentManager.beginTransaction()
                                sf.addToBackStack(null)
                                dialogFragment.show(sf, "dialog")
                            }
                        }

                        //Wednesday
                        //Chip
                        Wednesday = view.findViewById(R.id.chip_wed)
                        //Pick Ups Text View
                        textViewWednesdayPickup = view.findViewById(R.id.textViewWednesdayPickup)
                        textViewWednesdayDropoff = view.findViewById(R.id.textViewWednesdayDropoff)
                        //OnClick Chips
                        Wednesday.onClick {
                            if (Wednesday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                                Wednesday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                                Wednesday.setTextColor(resources.getColor(R.color.colorText1))
                            }else{
                                Wednesday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                                Wednesday.setTextColor(resources.getColor(R.color.colorText))
                                val dialogFragment = Util.getCustomTimeDialog(textViewWednesdayPickup, textViewWednesdayDropoff, Wednesday)
                                val sf = supportFragmentManager.beginTransaction()
                                sf.addToBackStack(null)
                                dialogFragment.show(sf, "dialog")
                            }
                        }

                        //Thursday
                        //Chip
                        Thursday = view.findViewById(R.id.chip_thu)
                        //Pick Ups Text View
                        textViewThursdayPickup = view.findViewById(R.id.textViewThursdayPickup)
                        textViewThursdayDropoff = view.findViewById(R.id.textViewThursdayDropoff)
                        //OnClick Chips
                        Thursday.onClick {
                            if (Thursday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                                Thursday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                                Thursday.setTextColor(resources.getColor(R.color.colorText1))
                            }else{
                                Thursday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                                Thursday.setTextColor(resources.getColor(R.color.colorText))
                                val dialogFragment = Util.getCustomTimeDialog(textViewThursdayPickup, textViewThursdayDropoff, Thursday)
                                val sf = supportFragmentManager.beginTransaction()
                                sf.addToBackStack(null)
                                dialogFragment.show(sf, "dialog")
                            }
                        }

                        //Friday
                        //Chip
                        Friday = view.findViewById(R.id.chip_fri)
                        //Pick Ups Text View
                        textViewFridayPickup = view.findViewById(R.id.textViewFridayPickup)
                        textViewFridayDropoff = view.findViewById(R.id.textViewFridayDropoff)
                        //OnClick Chips
                        Friday.onClick {
                            if (Friday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                                Friday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                                Friday.setTextColor(resources.getColor(R.color.colorText1))
                            }else{
                                Friday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                                Friday.setTextColor(resources.getColor(R.color.colorText))
                                val dialogFragment = Util.getCustomTimeDialog(textViewFridayPickup, textViewFridayDropoff, Friday)
                                val sf = supportFragmentManager.beginTransaction()
                                sf.addToBackStack(null)
                                dialogFragment.show(sf, "dialog")
                            }
                        }

                        //Saturday
                        //Chip
                        Saturday = view.findViewById(R.id.chip_sat)
                        //Pick Ups Text View
                        textViewSaturdayPickup = view.findViewById(R.id.textViewSaturdayPickup)
                        textViewSaturdayDropoff = view.findViewById(R.id.textViewSaturdayDropoff)
                        //OnClick Chips
                        Saturday.onClick {
                            if (Saturday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                                Saturday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                                Saturday.setTextColor(resources.getColor(R.color.colorText1))
                            }else{
                                Saturday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                                Saturday.setTextColor(resources.getColor(R.color.colorText))
                                val dialogFragment = Util.getCustomTimeDialog(textViewSaturdayPickup, textViewSaturdayDropoff, Saturday)
                                val sf = supportFragmentManager.beginTransaction()
                                sf.addToBackStack(null)
                                dialogFragment.show(sf, "dialog")
                            }
                        }

                        //Sunday
                        //Chip
                        Sunday = view.findViewById(R.id.chip_sun)
                        //Pick Ups Text View
                        textViewSundayPickup = view.findViewById(R.id.textViewSundayPickup)
                        textViewSundayDropoff = view.findViewById(R.id.textViewSundayDropoff)
                        //OnClick Chips
                        Sunday.onClick {
                            if (Sunday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                                Sunday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                                Sunday.setTextColor(resources.getColor(R.color.colorText1))
                            }else{

                                Sunday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                                Sunday.setTextColor(resources.getColor(R.color.colorText))
                                val dialogFragment = Util.getCustomTimeDialog(textViewSundayPickup, textViewSundayDropoff, Sunday)
                                val sf = supportFragmentManager.beginTransaction()
                                sf.addToBackStack(null)
                                dialogFragment.show(sf, "dialog")
                            }
                        }


                        //Bottom Sheet Continue Button
                        val btnContinue = view.findViewById<Button>(R.id.btn_Continue)
                        btnContinue.onClick {
                            insertRequest()
                        }



                        //Bus Part
//                    val intent = Intent(applicationContext, SeeRoutesActivity::class.java)
//                    intent.putExtra("DestLat", destinationLatLng.latitude)
//                    intent.putExtra("DestLong", destinationLatLng.longitude)
//                    intent.putExtra("PickupLat", marker.position.latitude)
//                    intent.putExtra("PickupLong", marker.position.longitude)
//                    startActivity(intent)
                    }

                }
            }
        })

    }

    //Function to insert request
    private fun insertRequest(){
        var listOfDays = mutableListOf<CarSharingDataModel>()

        //Monday
        if(Monday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)){
            if(textViewMondayPickup.text == ""){
                Toast.makeText(applicationContext,"Please select pick up time for Monday", Toast.LENGTH_LONG).show()
                return
            }else if(textViewMondayDropoff.text == ""){
                Toast.makeText(applicationContext,"Please select drop off time for Monday", Toast.LENGTH_LONG).show()
                return
            }else{
                var carSharingDataModel = CarSharingDataModel("Monday",
                    textViewMondayPickup.text.toString(),
                    textViewMondayDropoff.text.toString())
                daysDataModel.Monday = true
                listOfDays.add(carSharingDataModel)
            }
        }
        //Tuesday
        if(Tuesday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)){
            if(textViewTuesdayPickup.text == ""){
                Toast.makeText(applicationContext,"Please select pick up time for Tuesday", Toast.LENGTH_LONG).show()
                return
            }else if(textViewTuesdayDropoff.text == ""){
                Toast.makeText(applicationContext,"Please select drop off time for Tuesday", Toast.LENGTH_LONG).show()
                return
            }else{
                var carSharingDataModel     = CarSharingDataModel("Tuesday",
                    textViewTuesdayPickup.text.toString(),
                    textViewTuesdayDropoff.text.toString())
                daysDataModel.Tuesday = true
                listOfDays.add(carSharingDataModel)
            }
        }
        //Wednesday
        if(Wednesday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)){
            if(textViewWednesdayPickup.text == ""){
                Toast.makeText(applicationContext,"Please select pick up time for Wednesday", Toast.LENGTH_LONG).show()
                return
            }else if(textViewWednesdayDropoff.text == ""){
                Toast.makeText(applicationContext,"Please select drop off time for Wednesday", Toast.LENGTH_LONG).show()
                return
            }else{
                var carSharingDataModel = CarSharingDataModel("Wednesday",
                    textViewWednesdayPickup.text.toString(),
                    textViewWednesdayDropoff.text.toString())
                daysDataModel.Wednesday = true
                listOfDays.add(carSharingDataModel)
            }
        }

        //Thursday
        if(Thursday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)){
            if(textViewThursdayPickup.text == ""){
                Toast.makeText(applicationContext,"Please select pick up time for Thursday", Toast.LENGTH_LONG).show()
                return
            }else if(textViewThursdayDropoff.text == ""){
                Toast.makeText(applicationContext,"Please select drop off time for Thursday", Toast.LENGTH_LONG).show()
                return
            }else{
                var carSharingDataModel = CarSharingDataModel("Thursday",
                    textViewThursdayPickup.text.toString(),
                    textViewThursdayDropoff.text.toString())
                daysDataModel.Thursday = true
                listOfDays.add(carSharingDataModel)
            }
        }

        //Friday
        if(Friday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)){
            if(textViewFridayPickup.text == ""){
                Toast.makeText(applicationContext,"Please select pick up time for Friday", Toast.LENGTH_LONG).show()
                return
            }else if(textViewFridayDropoff.text == ""){
                Toast.makeText(applicationContext,"Please select drop off time for Friday", Toast.LENGTH_LONG).show()
                return
            }else{
                var carSharingDataModel = CarSharingDataModel("Friday",
                    textViewFridayPickup.text.toString(),
                    textViewFridayDropoff.text.toString())
                daysDataModel.Friday = true
                listOfDays.add(carSharingDataModel)
            }
        }

        //Saturday
        if(Saturday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)){
            if(textViewSaturdayPickup.text == ""){
                Toast.makeText(applicationContext,"Please select pick up time for Saturday", Toast.LENGTH_LONG).show()
                return
            }else if(textViewSaturdayDropoff.text == ""){
                Toast.makeText(applicationContext,"Please select drop off time for Saturday", Toast.LENGTH_LONG).show()
                return
            }else{
                var carSharingDataModel = CarSharingDataModel("Saturday",
                    textViewSaturdayPickup.text.toString(),
                    textViewSaturdayDropoff.text.toString())
                daysDataModel.Saturday = true
                listOfDays.add(carSharingDataModel)
            }
        }

        //Sunday
        if(Sunday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)){
            if(textViewSundayPickup.text == ""){
                Toast.makeText(applicationContext,"Please select pick up time for Sunday", Toast.LENGTH_LONG).show()
                return
            }else if(textViewSundayDropoff.text == ""){
                Toast.makeText(applicationContext,"Please select drop off time for Sunday", Toast.LENGTH_LONG).show()
                return
            }else{
                var carSharingDataModel = CarSharingDataModel("Sunday",
                    textViewSundayPickup.text.toString(),
                    textViewSundayDropoff.text.toString())
                daysDataModel.Sunday = true
                listOfDays.add(carSharingDataModel)
            }
        }

        var intent = Intent(applicationContext, ReviewInformationActivity::class.java)
        intent.putExtra("DaysData", daysDataModel)

        reviewInfo = ReviewInformationDataModel(marker.position.latitude, marker.position.longitude, pickUpAddress,
            destinationLatLng.latitude, destinationLatLng.longitude, destinationAddress,
            "Pending")
        intent.putExtra("ReviewData", reviewInfo)
        intent.putExtra("TimeDetails", listOfDays as Serializable)
        startActivity(intent)
    }

    //Function to get location
    private fun getDevicesLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        try {
            var task: Task<Location>? = fusedLocationProviderClient.lastLocation
            task!!.addOnCompleteListener {
                if (task.isSuccessful) {
                    var location = task.result
                    if(location != null){
                        moveCamera(
                            LatLng(location.latitude, location.longitude),
                            Util.getBiggerZoomValue()
                        )
                    }else{
                        var locationCallback = object : LocationCallback(){
                            override fun onLocationResult(p0: LocationResult) {
                                moveCamera(LatLng(p0.lastLocation.latitude, p0.lastLocation.longitude),
                                    Util.getBiggerZoomValue())
                            }
                        }
                        var locationRequest = LocationRequest()
                        locationRequest.priority = (LocationRequest.PRIORITY_HIGH_ACCURACY)
                        locationRequest.interval = (0)
                        locationRequest.fastestInterval = (0)
                        locationRequest.numUpdates = (1)
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)
                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest, locationCallback, Looper.myLooper()
                        )
                    }
                } else {
                    Toast.makeText(applicationContext, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: SecurityException) {
            Log.e("MapActivity", e.message.toString())
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnCameraIdleListener {
            if(!polyFlag){
                mMap.clear()
                val url = Util.getGeoCodeUrl(mMap.cameraPosition.target.latitude, mMap.cameraPosition.target.longitude, getString(R.string.google_maps_key))
                async {
                    val result = URL(url).readText()
                    uiThread {
                        val response = JSONObject(result)
                        val routes: JSONArray = response.getJSONArray("results")
                        pickUpAddress = routes.getJSONObject(0).getString("formatted_address")
                        autocompleteSupportFragment.setText(pickUpAddress)
                        addMarker(destinationLatLng,"Destination")
                        addMarker(mMap.cameraPosition.target, "Pickup")
                        customMarker!!.visibility = View.INVISIBLE
                    }
                }
            }
        }
        mMap.setOnCameraMoveListener {
            if(!polyFlag){
                customMarker!!.visibility = View.VISIBLE
            }

        }
        getDevicesLocation()
        init()
    }

    //Function to add markets
    private fun addMarker(latlng: LatLng, title:String?){
        val markerOptions = MarkerOptions().position(latlng).title(title)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_yello))
        marker = mMap.addMarker(markerOptions)
    }

    //Function to move Camera
    private fun moveCamera(latLng: LatLng, zoom:Float){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))

    }

    fun geolocate(place: Place){
        try {
            moveCamera(LatLng(place.latLng!!.latitude, place.latLng!!.longitude), Util.getBiggerZoomValue())
            addMarker(LatLng(place.latLng!!.latitude, place.latLng!!.longitude), place.address)
        }catch (e: IOException){
            Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}

package com.example.prototype

import android.app.TimePickerDialog
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.prototype.Utilities.Util
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
import java.net.URL
import java.text.SimpleDateFormat
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

    //GeoCoder
    private lateinit var geocoder: Geocoder

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
    private lateinit var txtViewMondayPickup:TextView
    private lateinit var txtViewTuesdayPickup:TextView
    private lateinit var txtViewWednesdayPickup:TextView
    private lateinit var txtViewThursdayPickup:TextView
    private lateinit var txtViewFridayPickup:TextView
    private lateinit var txtViewSatdayPickup:TextView
    private lateinit var txtViewSundayPickup:TextView

    //time dropoffs
    private lateinit var textViewMondayDropoff:TextView
    private lateinit var textViewTuedayDropoff:TextView
    private lateinit var textViewWeddayDropoff:TextView
    private lateinit var textViewThurdayDropoff:TextView
    private lateinit var textViewFridayDropoff:TextView
    private lateinit var textViewSatdayDropoff:TextView
    private lateinit var textViewSundayDropoff:TextView

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

        geocoder = Geocoder(applicationContext, Locale.getDefault())


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
                if (counter == 0) {
                    polylineOptions = PolylineOptions()
                    polylineOptions.color(Color.RED)
                    polylineOptions.width(5f)
                    val url = Util.getURL(
                        destinationLatLng,
                        LatLng(marker.position.latitude, marker.position.longitude),
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
                    val view =
                        layoutInflater.inflate(R.layout.activity_selectdays_bottomsheet, null)
                    //Chips
                    Monday = view.findViewById(R.id.chip_mon)
                    //Pick Ups Text View
                    txtViewMondayPickup = view.findViewById(R.id.txtViewMondayPickup)
                    textViewMondayDropoff = view.findViewById(R.id.textViewMondayDropoff)
                    //OnClick Chips
                    Monday.onClick {
                        if (Monday.chipBackgroundColor == getColorStateList(R.color.colorPrimary)) {
                            Monday.chipBackgroundColor = getColorStateList(R.color.chipBackgroundDisable)
                            Monday.setTextColor(resources.getColor(R.color.colorText1))
                        }else{
                            getTimerDialog(txtViewMondayPickup).show()
                            getTimerDialog(textViewMondayDropoff).show()
                            Toast.makeText(applicationContext, txtViewMondayPickup.text, Toast.LENGTH_LONG).show()
                            Monday.chipBackgroundColor = getColorStateList(R.color.colorPrimary)
                            Monday.setTextColor(resources.getColor(R.color.colorText))
                        }
                    }

                    Tuesday = view.findViewById(R.id.chip_tue)
                    Wednesday = view.findViewById(R.id.chip_wed)
                    Thursday = view.findViewById(R.id.chip_thu)
                    Friday = view.findViewById(R.id.chip_fri)
                    Saturday = view.findViewById(R.id.chip_sat)
                    Sunday = view.findViewById(R.id.chip_sun)
                    val dialog = BottomSheetDialog(v!!.context)
                    val btnContinue = view.findViewById<Button>(R.id.btn_Continue)
                    btnContinue.onClick {

                    }
                    dialog.setContentView(view)
                    dialog.show()


                     //Bus Part
//                    val intent = Intent(applicationContext, SeeRoutesActivity::class.java)
//                    intent.putExtra("DestLat", destinationLatLng.latitude)
//                    intent.putExtra("DestLong", destinationLatLng.longitude)
//                    intent.putExtra("PickupLat", marker.position.latitude)
//                    intent.putExtra("PickupLong", marker.position.longitude)
//                    startActivity(intent)
                }

            }
        })

    }

    //Function to get Timer Dialog
    private fun getTimerDialog(textView: TextView) : TimePickerDialog {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            textView.text = SimpleDateFormat("HH:mm a").format(cal.time)
        }
        return TimePickerDialog(
            this,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            false
        )
    }

    //Function to get location
    private fun getDevicesLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        try{
            var task: Task<Location>? = fusedLocationProviderClient.lastLocation
            task!!.addOnCompleteListener{
                if(task.isComplete){
                    val location = task.result
                    moveCamera(LatLng(location!!.latitude,location.longitude), Util.getBiggerZoomValue() , 1)
                    init()
                }else{
                    Log.d("Maps Activity", "Location Not Found")

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
                var address = geocoder.getFromLocation(mMap.cameraPosition.target.latitude,mMap.cameraPosition.target.longitude, 1)
                pickUpAddress = address.first().getAddressLine(0).toString()
                autocompleteSupportFragment.setText(address.first().getAddressLine(0).toString())
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
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_yello))
        marker = mMap.addMarker(markerOptions)
    }

    //Function to move Camera
    private fun moveCamera(latLng: LatLng, zoom:Float, moveType:Int = 0){
        when(moveType){
            0 -> mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
            1 -> mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
        }

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

package com.example.prototype.Utilities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.media.RingtoneManager
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.prototype.LoginActivity
import com.example.prototype.R
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        fun logout(context: Context): Intent {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }

        fun getGlobals(): com.example.prototype.companion.Companion.Globals {
            val globals = com.example.prototype.companion.Companion.Globals
            return globals
        }

        fun getFirebaseFireStore(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }

        fun getImageRequest(): Int {
            return 71
        }

        fun getCameraCode(): Int {
            return 3
        }

        fun getStorageRef(): StorageReference {
            return FirebaseStorage.getInstance()
                .reference.child("images/${getGlobals().user!!.uid}")
        }

        fun getStorageRefDepositSlip(requestID: Int): StorageReference {
            return FirebaseStorage.getInstance()
                .reference.child("depositSlips/${getGlobals().user!!.uid}/$requestID")
        }

        //Function to check if location is on
        fun isGPSEnable(locationManager: LocationManager): Boolean {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        fun getFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }


        fun getURL(from: LatLng, to: LatLng, key: String): String {
            val origin = "origin=" + from.latitude + "," + from.longitude
            val dest = "destination=" + to.latitude + "," + to.longitude
            val api_key = "key=${key}"
            val params = "$origin&$dest&$api_key"
            return "https://maps.googleapis.com/maps/api/directions/json?$params"
        }

        fun getGeoCodeUrl(lat: Double, lng: Double, key: String): String{
            return "https://maps.googleapis.com/maps/api/geocode/json?latlng=$lat,$lng&key=$key"
        }

        fun getDistance(pickupLatLng: LatLng, dropOffLatLng: LatLng): Float {
            val location = Location("")
            location.latitude = pickupLatLng.latitude
            location.longitude = pickupLatLng.longitude

            val locationtwo = Location("")
            locationtwo.latitude = dropOffLatLng.latitude
            locationtwo.longitude = dropOffLatLng.longitude

            return location.distanceTo(locationtwo)
        }

        fun getDistanceValueBetweenStops(): Int {
            return 1000
        }

        fun getBiggerZoomValue(): Float {
            return 15f
        }

        fun getFormattedDate(addDays: Long = 0): String {
            if (addDays == 0.toLong()) {
                return LocalDateTime.now()
                    .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
            } else {
                return LocalDateTime.now().plusDays(addDays)
                    .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
            }
        }

        fun verifyAvailableNetwork(activity: AppCompatActivity): Boolean {
            val connectivityManager =
                activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun getCalendarInstance(): Calendar {
            return Calendar.getInstance()
        }

        //Function to return alert dialog
        fun getAlertDialog(context: Context): AlertDialog.Builder {
            val alertDialog =
                AlertDialog.Builder(context, R.style.ThemeOverlay_MaterialComponents_Dialog)
            alertDialog.setTitle("Sath Chaloo")
            return alertDialog
        }

        //Function to get Custom Time Dialog
        fun getCustomTimeDialog(textViewPickUp: TextView, textViewDropOff: TextView, selectedChip: Chip): CustomTimeDialog{
            return CustomTimeDialog(textViewPickUp, textViewDropOff, selectedChip)
        }

        //Create notification Channels
        fun createNotificationChannels(context: Context){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val name = context.getString(R.string.androidChannelName)
                val descriptionText = context.getString(R.string.androidChannelDescription)
                val id = context.getString(R.string.androidChannelID)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(id, name, importance).apply {
                    description = descriptionText
                }
                getNotificationManager(context).createNotificationChannel(channel)
            }
        }

        //Get Notification Manager
        fun getNotificationManager(context: Context): NotificationManager{
            return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        }

        //Create notifications
        fun sendNotification(context: Context, body: String) : NotificationCompat.Builder{
            return NotificationCompat.Builder(context, context.getString(R.string.androidChannelID))
                .setSmallIcon(R.mipmap.ic_logo)
                .setColor(context.resources.getColor(R.color.colorAccent))
                .setContentTitle("Sath Chaloo")
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

    }
    }
}
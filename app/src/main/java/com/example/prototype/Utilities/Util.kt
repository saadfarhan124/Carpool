package com.example.prototype.Utilities

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Location
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.prototype.LoginActivity
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.io.File

class Util{
    companion object{



        fun logout(context: Context) : Intent{
            FirebaseAuth.getInstance().signOut()
            var intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }

        fun getGlobals(): com.example.prototype.companion.Companion.Globals{
            var globals = com.example.prototype.companion.Companion.Globals
            return globals
        }

        fun getFirebaseFireStore():FirebaseFirestore{
            return FirebaseFirestore.getInstance()
        }

        fun getImageRequest():Int{
            return 71
        }

        fun getStorageRef():StorageReference{
            return FirebaseStorage.getInstance()
                .reference.child("images/${getGlobals().user!!.uid}")
        }

        fun downloadDisplayPicture(){
            val localFile = File.createTempFile("${getGlobals().user!!.uid}", "jpg")
            var options = BitmapFactory.Options()
            getStorageRef().getFile(localFile).addOnSuccessListener {
                getGlobals().userImage = BitmapFactory.decodeByteArray(localFile.readBytes(),0, localFile.readBytes().size, options)
            }.addOnFailureListener{
            }
        }

         fun getURL(from : LatLng, to : LatLng, key:String) : String {
            val origin = "origin=" + from.latitude + "," + from.longitude
            val dest = "destination=" + to.latitude + "," + to.longitude
            val api_key = "key=${key}"
            val params = "$origin&$dest&$api_key"
            return "https://maps.googleapis.com/maps/api/directions/json?$params"
        }

        fun getDistance(pickupLatLng: LatLng, dropOffLatLng: LatLng) : Float{
            var location = Location("")
            location.latitude = pickupLatLng.latitude
            location.longitude = pickupLatLng.longitude

            var locationtwo = Location("")
            locationtwo.latitude = dropOffLatLng.latitude
            locationtwo.longitude = dropOffLatLng.longitude

           return location.distanceTo(locationtwo)
        }

        fun getDistanceValueBetweenStops():Int{
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

        fun verifyAvailableNetwork(activity: AppCompatActivity):Boolean{
            val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }
    }
}
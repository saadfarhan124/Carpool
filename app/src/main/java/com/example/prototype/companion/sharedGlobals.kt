package com.example.prototype.companion

import android.graphics.Bitmap
import android.net.Uri
import com.example.prototype.dataModels.UserDataModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseUser

class Companion{
    companion object Globals{
        var user : FirebaseUser? = null
        var userDataModel:UserDataModel? = null
        var imageUri: Uri? = null
        var userImage: Bitmap? = null
        var pickUpSpot: LatLng? = null
        var dropOffSpot: LatLng? = null
        var distanceFromPickUp: Float = 0f
        var distanceFromDropOff: Float = 0f
    }
}
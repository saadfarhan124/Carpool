package com.example.prototype.companion

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.auth.FirebaseUser

class Companion{
    companion object Globals{
        var user : FirebaseUser? = null
        var imageUri: Uri? = null
        var userImage: Bitmap? = null
    }
}
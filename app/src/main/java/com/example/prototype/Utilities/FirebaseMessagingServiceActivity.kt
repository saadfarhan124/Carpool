package com.example.prototype.Utilities

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService


class FirebaseMessagingServiceActivity: FirebaseMessagingService() {

    override fun onNewToken(token: String?) {
        Log.d("see", "Refreshed token: " + token!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

    }
}
package com.example.prototype.Services

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.prototype.Utilities.Util

class PushNotificationForegroundService : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent != null){
            val bundle = intent.extras

            if(bundle != null){
                val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(0, Util.sendNotification(context, bundle.getString("gcm.notification.body").toString()).build())
            }

        }

    }

}
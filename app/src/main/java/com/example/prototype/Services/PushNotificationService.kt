package com.example.prototype.Services

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.prototype.R
import com.example.prototype.Utilities.Util
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        if(p0 != null){
            if(p0.data != null){
//                with(NotificationManagerCompat.from(this)){
//                    notify(0, Util.sendNotification(this@PushNotificationService, p0.data.toString()).build())
//                }
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(0, Util.sendNotification(applicationContext, p0.data.toString()).build())
            }
        }
    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)

    }


}

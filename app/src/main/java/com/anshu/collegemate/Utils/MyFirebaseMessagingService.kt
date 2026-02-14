package com.anshu.collegemate.Utils

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.anshu.collegemate.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onMessageReceived(message: RemoteMessage) {
        android.util.Log.e("FCM_TEST", "onMessageReceived CALLED")
        super.onMessageReceived(message)



        val title = message.data["title"]?: "New Announcement"
        val body = message.data["body"] ?: ""

        val notification = NotificationCompat.Builder(this, "ANNOUNCEMENT_CHANNEL")
            .setSmallIcon(R.drawable.logo_foreground) // TEMP icon
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this).notify(1, notification)
    }


}

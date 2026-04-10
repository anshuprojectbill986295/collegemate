package com.anshu.collegemate.Utils

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.anshu.collegemate.MainActivity
import com.anshu.collegemate.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onMessageReceived(message: RemoteMessage) {
        android.util.Log.e("FCM_TEST", "onMessageReceived CALLED")
        super.onMessageReceived(message)

        val title = message.data["title"] ?: "New Announcement"
        val body = message.data["body"] ?: ""
        val type = message.data["type"] ?: "NONE"

        // 🔹 FIX 1: ALWAYS use your App Logo for the small icon!
        // Make sure you have a monochrome/transparent version of your logo in your drawables.
        // E.g., R.drawable.ic_stat_collegemate_logo
        val smallIconId = R.drawable.ic_launcher_foreground // Replace this with your monochrome app logo

        // 🔹 FIX 2: Move the dynamic visual to the Large Icon.
        // This puts your custom images on the right side of the notification.
        val largeIconBitmap = if (type == "CANCELLATION") {
            BitmapFactory.decodeResource(resources, R.drawable.cancelicon)
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.generalnotificationicon)
            // Fallback to app icon if you don't want a general icon:
            // BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val action = NotificationCompat.Action.Builder(
            0,
            if (type == "CANCELLATION") "Check Schedule" else "View Details",
            pendingIntent
        ).build()

        // 3. Build the Ultimate Notification
        val notification = NotificationCompat.Builder(this, "ANNOUNCEMENT_CHANNEL")
            .setSmallIcon(smallIconId) // Now guaranteed to be your app logo
            .setLargeIcon(largeIconBitmap) // Custom image on the right
            .setContentTitle(title) // Text with emoji from Firebase
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(action)
            .setShowWhen(true)
            .setColor(Color.parseColor("#2196F3"))
            .build()

        NotificationManagerCompat.from(this).notify(System.currentTimeMillis().toInt(), notification)
    }
}
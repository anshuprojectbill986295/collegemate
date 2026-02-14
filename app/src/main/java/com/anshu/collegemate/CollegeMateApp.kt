package com.anshu.collegemate

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class CollegeMateApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "ANNOUNCEMENT_CHANNEL",
                "Announcements",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "College announcements"

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}

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
            val manager = getSystemService(NotificationManager::class.java)

            val announcementChannel = NotificationChannel(
                "ANNOUNCEMENT_CHANNEL",
                "Announcements",
                NotificationManager.IMPORTANCE_HIGH
            )
            announcementChannel.description = "College announcements"
            manager.createNotificationChannel(announcementChannel)

            val birthdayChannel = NotificationChannel(
                "BIRTHDAY_CHANNEL",
                "Birthdays",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            birthdayChannel.description = "Student birthday greetings"
            manager.createNotificationChannel(birthdayChannel)
        }
    }
}

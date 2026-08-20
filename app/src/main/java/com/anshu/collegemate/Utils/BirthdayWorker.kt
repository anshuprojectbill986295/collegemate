package com.anshu.collegemate.Utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.anshu.collegemate.Data.Model.HomeScreen.BirthdaySeed
import com.anshu.collegemate.MainActivity
import com.anshu.collegemate.R
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BirthdayWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: return Result.success()
        
        if (BirthdaySeed.isBirthdayToday(userEmail)) {
            val student = BirthdaySeed.getBirthdayForUser(userEmail)
            if (student != null) {
                val today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                if (shouldNotify(today)) {
                    showNotification(student.name)
                    markAsNotified(today)
                }
            }
        }

        // Schedule next check for tomorrow
        BirthdayScheduler.scheduleNextCheck(applicationContext)
        
        return Result.success()
    }

    private fun shouldNotify(dateKey: String): Boolean {
        val prefs = applicationContext.getSharedPreferences("birthday_prefs", Context.MODE_PRIVATE)
        val lastNotified = prefs.getString("last_birthday_notified", "")
        return lastNotified != dateKey
    }

    private fun markAsNotified(dateKey: String) {
        val prefs = applicationContext.getSharedPreferences("birthday_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("last_birthday_notified", dateKey).apply()
    }

    private fun showNotification(name: String) {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(applicationContext, "BIRTHDAY_CHANNEL")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("🎂 Happy Birthday!")
            .setContentText("Wishing you a wonderful birthday, ${DateTimeUtil.toTitleCase(name)}! 🎉")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        try {
            NotificationManagerCompat.from(applicationContext).notify(1001, notification)
        } catch (e: SecurityException) {
            // Permission not granted, ignore
        }
    }
}

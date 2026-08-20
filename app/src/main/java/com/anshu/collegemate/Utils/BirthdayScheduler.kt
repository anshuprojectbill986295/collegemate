package com.anshu.collegemate.Utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit

object BirthdayScheduler {
    private const val WORK_NAME = "BirthdayCheckWork"

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleNextCheck(context: Context) {
        val delay = calculateDelayUntilMidnight()
        
        val workRequest = OneTimeWorkRequestBuilder<BirthdayWorker>()
            .setInitialDelay(delay, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateDelayUntilMidnight(): Long {
        val now = LocalDateTime.now()
        val nextMidnight = now.toLocalDate().plusDays(1).atTime(LocalTime.MIDNIGHT)
        return Duration.between(now, nextMidnight).seconds
    }
}

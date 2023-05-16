package com.luisfagundes.data.local.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.luisfagundes.data.local.services.PushNotificationManager.NOTIFICATION_DATA_KEY
import com.luisfagundes.domain.models.NotificationData
import com.luisfagundes.domain.models.ScheduleData
import com.luisfagundes.domain.services.AlarmScheduler

class AlarmSchedulerImpl(
    private val context: Context,
    private val alarmManager: AlarmManager,
) : AlarmScheduler {
    override fun scheduleAlarm(
        scheduleData: ScheduleData,
        notificationData: NotificationData,
    ) {
        val intent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra(NOTIFICATION_DATA_KEY, notificationData)
        }
        val pendingIntent = createPendingIntent(intent)
        val initialTime = SystemClock.elapsedRealtime()
        val timeInterval = calculateTimeInterval(scheduleData)

        try {
            alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                initialTime,
                timeInterval,
                pendingIntent,
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    private fun calculateTimeInterval(
        scheduleData: ScheduleData,
    ): Long {
        val intervalHourUnit = AlarmManager.INTERVAL_HOUR
        val intervalHour = scheduleData.intervalHours
        return (intervalHourUnit * intervalHour)
    }

    private fun createPendingIntent(intent: Intent) =
        PendingIntent.getBroadcast(
            context,
            PENDING_INTENT_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

    companion object {
        const val PENDING_INTENT_REQUEST_CODE = 1
    }
}

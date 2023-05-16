package com.luisfagundes.data.local.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.core.content.ContextCompat
import com.luisfagundes.data.local.services.AlarmBroadcastReceiver
import com.luisfagundes.data.local.services.PushNotificationManager
import com.luisfagundes.domain.models.NotificationData
import com.luisfagundes.domain.models.ScheduleData
import timber.log.Timber

const val PENDING_INTENT_REQUEST_CODE = 1
const val SCHEDULE_DATA_KEY = "SCHEDULE_DATA_KEY"

object AlarmHelper {
    fun setAlarm(
        context: Context,
        notificationData: NotificationData,
        scheduleData: ScheduleData,
        triggerTime: Long,
    ) {
        val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java)
        val intent = createIntent(context, notificationData, scheduleData)
        val pendingIntent = createPendingIntent(context, intent)

        try {
            alarmManager?.setExactAndAllowWhileIdle(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime,
                pendingIntent,
            )
        } catch (exception: Exception) {
            Timber.d("AlarmHelper", "Error setting alarm", exception)
        }
    }

    private fun createIntent(
        context: Context,
        notificationData: NotificationData,
        scheduleData: ScheduleData,
    ): Intent {
        return Intent(context, AlarmBroadcastReceiver::class.java).apply {
            putExtra(PushNotificationManager.NOTIFICATION_DATA_KEY, notificationData)
            putExtra(SCHEDULE_DATA_KEY, scheduleData)
        }
    }

    private fun createPendingIntent(
        context: Context,
        intent: Intent,
    ) = PendingIntent.getBroadcast(
        context,
        PENDING_INTENT_REQUEST_CODE,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )

    fun calculateNextTriggerTime(
        scheduleData: ScheduleData,
    ): Long {
        val currentTime = SystemClock.elapsedRealtime()
        val intervalHourUnit = AlarmManager.INTERVAL_HOUR
        val intervalHour = scheduleData.intervalHours
        return currentTime + (intervalHourUnit * intervalHour)
    }
}

package com.luisfagundes.data.local.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.luisfagundes.data.local.services.PushNotificationManager.NOTIFICATION_DATA_KEY
import com.luisfagundes.data.local.utils.AlarmHelper.calculateNextTriggerTime
import com.luisfagundes.data.local.utils.AlarmHelper.setAlarm
import com.luisfagundes.data.local.utils.SCHEDULE_DATA_KEY
import com.luisfagundes.domain.models.NotificationData
import com.luisfagundes.domain.models.ScheduleData
import com.luisfagundes.framework.extension.parcelable
import timber.log.Timber

class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { safeContext ->
            intent?.let { safeIntent ->
                val notificationData = safeIntent.parcelable<NotificationData>(
                    NOTIFICATION_DATA_KEY,
                ) ?: return

                val scheduleData = safeIntent.parcelable<ScheduleData>(
                    SCHEDULE_DATA_KEY,
                ) ?: return

                try {
                    val notification = PushNotificationManager.createNotification(
                        safeContext,
                        notificationData,
                    )

                    PushNotificationManager.notify(
                        context = safeContext,
                        notification = notification,
                        notificationData = notificationData,
                    )

                    setAlarm(
                        context = safeContext,
                        notificationData = notificationData,
                        scheduleData = scheduleData,
                        triggerTime = calculateNextTriggerTime(scheduleData),
                    )
                } catch (exception: Exception) {
                    Timber.e("AlarmBroadcastReceiver", "Error handling notification", exception)
                }
            }
        }
    }
}

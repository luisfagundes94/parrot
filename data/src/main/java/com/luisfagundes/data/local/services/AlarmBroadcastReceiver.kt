package com.luisfagundes.data.local.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.luisfagundes.data.local.services.PushNotificationManagerImpl.Companion.NOTIFICATION_DATA_KEY
import com.luisfagundes.data.local.utils.AlarmHelper.calculateNextTriggerTime
import com.luisfagundes.data.local.utils.AlarmHelper.setAlarm
import com.luisfagundes.data.local.utils.SCHEDULE_DATA_KEY
import com.luisfagundes.domain.managers.PushNotificationManager
import com.luisfagundes.domain.models.NotificationData
import com.luisfagundes.domain.models.ScheduleData
import com.luisfagundes.framework.extension.parcelable
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AlarmBroadcastReceiver : BroadcastReceiver() {

    @Inject lateinit var pushNotificationManager: PushNotificationManager

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
                    val notification = pushNotificationManager.createNotification(
                        safeContext,
                        notificationData,
                    )

                    pushNotificationManager.notify(
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
                    Timber.e(
                        "AlarmBroadcastReceiver",
                        "Error handling notification",
                        exception,
                    )
                }
            }
        }
    }
}

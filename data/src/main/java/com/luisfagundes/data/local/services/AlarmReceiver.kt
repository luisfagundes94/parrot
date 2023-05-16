package com.luisfagundes.data.local.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.luisfagundes.data.local.services.AlarmSchedulerImpl.Companion.SCHEDULE_DATA_KEY
import com.luisfagundes.data.local.services.NotificationManager.Companion.NOTIFICATION_DATA_KEY
import com.luisfagundes.domain.models.NotificationData
import com.luisfagundes.domain.models.ScheduleData
import com.luisfagundes.domain.services.AlarmScheduler
import com.luisfagundes.framework.extension.parcelable
import javax.inject.Inject

class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var scheduler: AlarmScheduler

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        val scheduleData = intent.parcelable<ScheduleData>(
            SCHEDULE_DATA_KEY,
        ) ?: return
        val notificationData = intent.parcelable<NotificationData>(
            NOTIFICATION_DATA_KEY,
        ) ?: return

        val notification = notificationManager.createNotification(context, notificationData)
        notificationManager.notify(
            context = context,
            notification = notification,
            notificationData = notificationData,
        )

        scheduler.scheduleAlarm(scheduleData, notificationData)
    }
}

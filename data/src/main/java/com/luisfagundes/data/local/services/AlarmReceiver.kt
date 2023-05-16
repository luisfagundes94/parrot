package com.luisfagundes.data.local.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.luisfagundes.data.local.services.NotificationSchedulerImpl.Companion.NOTIFICATION_DATA_KEY
import com.luisfagundes.data.local.services.NotificationSchedulerImpl.Companion.SCHEDULE_DATA_KEY
import com.luisfagundes.domain.models.NotificationChannelInfo
import com.luisfagundes.domain.models.NotificationData
import com.luisfagundes.domain.models.ScheduleData
import com.luisfagundes.domain.services.NotificationScheduler
import com.luisfagundes.framework.extension.parcelable
import javax.inject.Inject

class AlarmReceiver: BroadcastReceiver() {

    @Inject
    lateinit var scheduler: NotificationScheduler

    override fun onReceive(context: Context, intent: Intent) {
        val scheduleData = intent.parcelable<ScheduleData>(
            SCHEDULE_DATA_KEY
        ) ?: return
        val notificationData = intent.parcelable<NotificationData>(
            NOTIFICATION_DATA_KEY
        ) ?: return

        val notification = createNotification(context, notificationData)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) return
        }

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationData.id, notification)

        scheduler.scheduleNotification(scheduleData, notificationData)
    }

    private fun createNotification(
        context: Context,
        notificationData: NotificationData
    ) = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(notificationData.smallIconId)
        .setLargeIcon(notificationData.largeIcon)
        .setContentTitle(notificationData.title)
        .setContentText(notificationData.content)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setGroup(WORD_REMINDER_GROUP)
        .build()

    companion object {
        fun createNotificationChannel(
            notificationChannelInfo: NotificationChannelInfo,
            context: Context
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = notificationChannelInfo.name
                val descriptionText = notificationChannelInfo.description
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(
                    notificationChannelInfo.id,
                    name,
                    importance
                ).apply {
                    description = descriptionText
                }
                val notificationManager = getSystemService(
                    context, NotificationManager::class.java
                ) as NotificationManager

                notificationManager.createNotificationChannel(channel)
            }
        }
        const val CHANNEL_ID = "1"
        const val WORD_REMINDER_GROUP = "word_reminder_group"
    }
}
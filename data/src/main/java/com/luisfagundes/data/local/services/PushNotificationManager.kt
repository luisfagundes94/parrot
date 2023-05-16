package com.luisfagundes.data.local.services

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.luisfagundes.domain.models.NotificationChannelInfo
import com.luisfagundes.domain.models.NotificationData

object PushNotificationManager {

    private const val CHANNEL_ID = "1"
    private const val WORD_REMINDER_GROUP = "word_reminder_group"
    const val NOTIFICATION_DATA_KEY = "notification_info"

    fun notify(
        notification: Notification,
        context: Context,
        notificationData: NotificationData,
    ) {
        val notificationManager = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        notificationManager.notify(notificationData.id, notification)
    }

    fun createNotification(
        context: Context,
        notificationData: NotificationData,
    ) = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(notificationData.smallIconId)
        .setLargeIcon(notificationData.largeIcon)
        .setContentTitle(notificationData.title)
        .setContentText(notificationData.content)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setGroup(WORD_REMINDER_GROUP)
        .build()

    fun createNotificationChannel(
        notificationChannelInfo: NotificationChannelInfo,
        context: Context,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = notificationChannelInfo.name
            val descriptionText = notificationChannelInfo.description
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                notificationChannelInfo.id,
                name,
                importance,
            ).apply {
                description = descriptionText
            }
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}

package com.luisfagundes.domain.managers

import android.app.Notification
import android.content.Context
import com.luisfagundes.domain.models.NotificationChannelInfo
import com.luisfagundes.domain.models.NotificationData

interface PushNotificationManager {
    fun notify(
        context: Context,
        notificationData: NotificationData,
        notification: Notification,
    )

    fun createNotification(
        context: Context,
        notificationData: NotificationData,
    ): Notification

    fun cancelNotification(
        context: Context,
        id: Int,
    )

    fun createNotificationChannel(
        context: Context,
        notificationChannelInfo: NotificationChannelInfo,
    )
}

package com.luisfagundes.data.local.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.luisfagundes.data.local.services.PushNotificationManager.NOTIFICATION_DATA_KEY
import com.luisfagundes.domain.models.NotificationData
import com.luisfagundes.framework.extension.parcelable

class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationData = intent?.parcelable<NotificationData>(
            NOTIFICATION_DATA_KEY,
        ) ?: return

        val notification = context?.let {
            PushNotificationManager.createNotification(
                context,
                notificationData,
            )
        }

        notification?.let {
            PushNotificationManager.notify(
                context = context,
                notification = notification,
                notificationData = notificationData,
            )
        }
    }
}

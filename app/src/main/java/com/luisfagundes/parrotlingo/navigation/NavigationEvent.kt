package com.luisfagundes.parrotlingo.navigation

import android.content.Context
import com.luisfagundes.domain.models.NotificationChannelInfo

sealed class NavigationEvent {
    data class CreateNotificationChannel(
        val context: Context,
        val notificationChannelInfo: NotificationChannelInfo,
    ) : NavigationEvent()
}

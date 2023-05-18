package com.luisfagundes.parrotlingo.navigation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.luisfagundes.domain.managers.PushNotificationManager
import com.luisfagundes.domain.models.NotificationChannelInfo
import com.luisfagundes.provider.ThemeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val pushNotificationManager: PushNotificationManager,
    private val themeProvider: ThemeProvider,
) : ViewModel() {

    fun onEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.CreateNotificationChannel -> createNotificationChannel(
                context = event.context,
                notificationChannelInfo = event.notificationChannelInfo,
            )
        }
    }

    private fun createNotificationChannel(
        context: Context,
        notificationChannelInfo: NotificationChannelInfo,
    ) {
        pushNotificationManager.createNotificationChannel(
            context = context,
            notificationChannelInfo = notificationChannelInfo,
        )
    }

    fun getThemeProvider() = themeProvider
}

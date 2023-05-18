package com.luisfagundes.domain.usecases

import android.content.Context
import com.luisfagundes.domain.managers.PushNotificationManager
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class CancelNotification @Inject constructor(
    private val context: Context,
    private val pushNotificationManager: PushNotificationManager,
) {
    operator fun invoke(id: Int) {
        pushNotificationManager.cancelNotification(context, id)
    }
}

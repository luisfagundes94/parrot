package com.luisfagundes.domain.usecases

import com.luisfagundes.domain.models.NotificationData
import com.luisfagundes.domain.models.ScheduleData
import com.luisfagundes.domain.services.NotificationScheduler
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ScheduleNotification @Inject constructor(
    private val notificationScheduler: NotificationScheduler
) {
    operator fun invoke(
        scheduleData: ScheduleData,
        notificationData: NotificationData
    ) {
        notificationScheduler.scheduleNotification(scheduleData, notificationData)
    }
}
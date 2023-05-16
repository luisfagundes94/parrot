package com.luisfagundes.domain.services

import com.luisfagundes.domain.models.NotificationData
import com.luisfagundes.domain.models.ScheduleData

interface AlarmScheduler {
    fun scheduleAlarm(
        scheduleData: ScheduleData,
        notificationData: NotificationData,
    )
}

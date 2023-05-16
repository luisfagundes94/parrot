package com.luisfagundes.domain.services

import com.luisfagundes.domain.models.NotificationData
import com.luisfagundes.domain.models.ScheduleData

interface NotificationScheduler {
  fun scheduleNotification(
    scheduleData: ScheduleData,
    notificationData: NotificationData,
  )
}

package com.luisfagundes.data.local.services

import android.content.Context
import android.os.SystemClock
import com.luisfagundes.data.local.utils.AlarmHelper.setAlarm
import com.luisfagundes.domain.models.NotificationData
import com.luisfagundes.domain.models.ScheduleData
import com.luisfagundes.domain.services.AlarmScheduler

class AlarmSchedulerImpl(
    private val context: Context,
) : AlarmScheduler {
    override fun scheduleAlarm(
        scheduleData: ScheduleData,
        notificationData: NotificationData,
    ) {
        setAlarm(
            context = context,
            notificationData = notificationData,
            scheduleData = scheduleData,
            triggerTime = SystemClock.elapsedRealtime(),
        )
    }
}

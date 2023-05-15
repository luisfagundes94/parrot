package com.luisfagundes.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

private const val DEFAULT_INTERVAL_HOUR = 2
private const val DEFAULT_SPECIFIC_HOUR_OF_DAY = 20

@Parcelize
data class ScheduleData(
    val intervalHours: Int = DEFAULT_INTERVAL_HOUR,
    val specificHourOfDay: Int = DEFAULT_SPECIFIC_HOUR_OF_DAY,
    val isRepeatable: Boolean = true
): Parcelable

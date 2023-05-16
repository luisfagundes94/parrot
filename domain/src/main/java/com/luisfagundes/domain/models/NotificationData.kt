package com.luisfagundes.domain.models

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationData(
    val id: Int,
    val smallIconId: Int,
    val largeIcon: Bitmap? = null,
    val title: String,
    val content: String,
) : Parcelable

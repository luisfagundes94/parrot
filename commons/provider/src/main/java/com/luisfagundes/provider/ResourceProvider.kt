package com.luisfagundes.provider

import android.graphics.Bitmap
import androidx.annotation.StringRes

interface ResourceProvider {
  fun getString(@StringRes id: Int): String
  fun getAppIconId(): Int
  fun getAppIconBitmap(): Bitmap?
}

package com.luisfagundes.core.common.tools

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes resId: Int, vararg params: String = emptyArray()): String
    fun getColor(colorId: Int): Int
    fun getDrawable(@DrawableRes drawableResId: Int): Drawable?
}
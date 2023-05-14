package com.luisfagundes.provider

import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes id: Int): String
    fun getAppIconId(): Int
}
package com.luisfagundes.framework.extension

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable

const val TIRAMISU = 33

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= TIRAMISU -> getParcelableExtra(key, T::class.java)
    else -> getParcelableExtra(key) as? T
}

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= TIRAMISU -> getParcelable(key, T::class.java)
    else -> getParcelable(key) as? T
}

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Bundle.parcelableArrayList(
    key: String,
): ArrayList<T>? = when {
    SDK_INT >= TIRAMISU -> getParcelableArrayList(key, T::class.java)
    else -> getParcelableArrayList(key)
}

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Intent.parcelableArrayList(
    key: String,
): ArrayList<T>? = when {
    SDK_INT >= TIRAMISU -> getParcelableArrayListExtra(key, T::class.java)
    else -> getParcelableArrayListExtra(key)
}

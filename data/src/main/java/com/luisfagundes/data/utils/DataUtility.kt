package com.luisfagundes.data.utils

import android.content.Context
import timber.log.Timber
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        Timber.d("Error reading file: $ioException")
        return null
    }
    return jsonString
}

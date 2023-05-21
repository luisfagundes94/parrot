package com.luisfagundes.framework.extension

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import timber.log.Timber

fun Context.appVersion(): String {
    return try {
        packageManager.getPackageInfoCompat(packageName).versionName
    } catch (ex: PackageManager.NameNotFoundException) {
        Timber.d("VersionName not found", ex)
        ""
    }
}

@RequiresApi(Build.VERSION_CODES.P)
fun Context.appVersionCode(): Long {
    return try {
        packageManager.getPackageInfoCompat(packageName).longVersionCode
    } catch (ex: PackageManager.NameNotFoundException) {
        Timber.d("LongVersionCode not found", ex)
        0L
    }
}

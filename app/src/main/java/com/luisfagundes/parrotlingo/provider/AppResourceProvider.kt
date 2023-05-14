package com.luisfagundes.parrotlingo.provider

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.luisfagundes.provider.ResourceProvider

class AppResourceProvider(
    private val context: Context
): ResourceProvider {
    override fun getString(id: Int): String {
        return context.getString(id)
    }

    override fun getAppIconId(): Int {
        val packageName = context.packageName
        return try {
            val applicationInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(0))
            } else {
                context.packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            }
            applicationInfo.icon
        } catch (e: PackageManager.NameNotFoundException) {
            -1
        }
    }
}



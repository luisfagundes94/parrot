package com.luisfagundes.parrotlingo.provider

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.PackageManagerCompat
import com.luisfagundes.framework.extension.getPackageInfoCompat
import com.luisfagundes.provider.ResourceProvider

class AppResourceProvider(
    private val context: Context
) : ResourceProvider {
    override fun getString(id: Int): String {
        return context.getString(id)
    }

    override fun getAppIconId(): Int {
        val packageName = context.packageName
        val applicationInfo = context.packageManager.getPackageInfoCompat(
            packageName
        ).applicationInfo

        return applicationInfo.icon
    }

    override fun getAppIconBitmap(): Bitmap {
        val packageName = context.packageName
        val applicationInfo = context.packageManager.getPackageInfoCompat(
            packageName
        ).applicationInfo

        val drawable = applicationInfo.loadIcon(context.packageManager)
        return drawable.toBitmap()
    }

    private fun Drawable.toBitmap(): Bitmap {
        if (this is BitmapDrawable) {
            return this.bitmap
        }

        val bitmap =
            Bitmap.createBitmap(this.intrinsicWidth, this.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        this.setBounds(0, 0, canvas.width, canvas.height)
        this.draw(canvas)

        return bitmap
    }
}



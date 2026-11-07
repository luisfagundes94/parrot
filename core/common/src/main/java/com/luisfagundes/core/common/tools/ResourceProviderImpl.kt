package com.luisfagundes.core.common.tools

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(
    private val context: Context
) : ResourceProvider {
    override fun getString(resId: Int, vararg params: String): String {
        return context.resources.getString(resId, params)
    }

    override fun getColor(colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

    override fun getDrawable(drawableResId: Int): Drawable? {
        return AppCompatResources.getDrawable(context, drawableResId)
    }
}
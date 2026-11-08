package com.luisfagundes.feature.search.impl.tools

import android.graphics.drawable.Drawable
import com.luisfagundes.core.common.tools.ResourceProvider

internal class FakeResourceProvider : ResourceProvider {
    override fun getString(resId: Int, vararg params: String) = ""
    override fun getColor(colorId: Int) = 0
    override fun getDrawable(drawableResId: Int): Drawable? = null
}

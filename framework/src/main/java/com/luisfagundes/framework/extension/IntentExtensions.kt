package com.luisfagundes.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle

fun Context.getActivity(): Activity? {
    if (this is ContextWrapper) {
        return this as? Activity
    }
    return null
}

fun Activity.launchActivity(
    packageName: String,
    className: String,
    flags: Int = -1,
    bundle: Bundle? = null
) {
    val intent = Intent(Intent.ACTION_VIEW).setClassName(packageName, className)
    if (flags != -1) {
        intent.flags = flags
    }
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}
package com.luisfagundes.framework.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

@Composable
fun showToast(
    shouldShow: Boolean = true,
    message: String,
    duration: Int = Toast.LENGTH_SHORT,
) {
    if (!shouldShow) return

    val context = LocalContext.current
    LaunchedEffect(key1 = message, key2 = duration) {
        Toast.makeText(context, message, duration).show()
    }
}

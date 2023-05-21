package com.luisfagundes.framework.utils

import androidx.navigation.NavOptionsBuilder

fun NavOptionsBuilder.commonNavigationOptions() {
    launchSingleTop = true
    restoreState = true
}

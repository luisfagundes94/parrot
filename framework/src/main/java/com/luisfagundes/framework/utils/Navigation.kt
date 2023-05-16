package com.luisfagundes.framework.utils

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

fun NavOptionsBuilder.commonNavigationOptions(navController: NavHostController) {
  popUpTo(navController.graph.findStartDestination().id) { saveState = true }
  launchSingleTop = true
  restoreState = true
}

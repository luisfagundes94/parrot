package com.luisfagundes.parrotlingo.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.luisfagundes.parrotlingo.navigation.BottomBarScreen
import com.luisfagundes.settings.SettingsScreen

fun NavGraphBuilder.registerSettingsRoute() {
    composable(route = BottomBarScreen.Settings.route) {
        SettingsScreen()
    }
}
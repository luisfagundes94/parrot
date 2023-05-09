package com.luisfagundes.parrotlingo.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.luisfagundes.parrotlingo.navigation.BottomBarScreen
import com.luisfagundes.saved.SavedScreen

fun NavGraphBuilder.savedRoute() {
    composable(route = BottomBarScreen.Saved.route) {
        SavedScreen()
    }
}
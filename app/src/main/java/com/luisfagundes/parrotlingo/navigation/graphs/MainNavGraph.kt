package com.luisfagundes.parrotlingo.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.luisfagundes.parrotlingo.navigation.BottomBarScreen
import com.luisfagundes.parrotlingo.navigation.routes.languageListRoute
import com.luisfagundes.parrotlingo.navigation.routes.savedRoute
import com.luisfagundes.parrotlingo.navigation.routes.settingsRoute
import com.luisfagundes.parrotlingo.navigation.routes.translationRoute

@Composable
fun MainNavGraph(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.MAIN,
        startDestination = BottomBarScreen.Translation.route
    ) {
        translationRoute(navController)
        savedRoute()
        settingsRoute()
        languageListRoute(navController)
    }
}






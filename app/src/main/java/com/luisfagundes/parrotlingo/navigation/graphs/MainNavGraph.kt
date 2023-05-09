package com.luisfagundes.parrotlingo.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.luisfagundes.parrotlingo.navigation.BottomBarScreen
import com.luisfagundes.parrotlingo.navigation.routes.registerLanguageListRoute
import com.luisfagundes.parrotlingo.navigation.routes.registerSavedRoute
import com.luisfagundes.parrotlingo.navigation.routes.registerSettingsRoute
import com.luisfagundes.parrotlingo.navigation.routes.registerTranslationRoute

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
        registerTranslationRoute(navController)
        registerSavedRoute()
        registerSettingsRoute()
        registerLanguageListRoute(navController)
    }
}






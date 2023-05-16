package com.luisfagundes.parrotlingo.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.luisfagundes.parrotlingo.navigation.MainScreen

@Composable
fun RootNavGraph(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.MAIN,
    ) {
        composable(route = Graph.MAIN) {
            MainScreen()
        }
    }
}

object Graph {
    const val ROOT = "root"
    const val MAIN = "main"
}

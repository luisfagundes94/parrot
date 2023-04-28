package com.luisfagundes.parrotlingo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.luisfagundes.saved.SavedScreen
import com.luisfagundes.settings.SettingsScreen
import com.luisfagundes.translation.TranslationScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigationRoot() {
    val navController = rememberNavController()
    val screens = getScreenList()

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                screens.forEach { screen ->
                    NavigationBarItem(
                        label = {
                            Text(
                                text = stringResource(screen.screenNameId),
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        icon = {
                            Icon(
                                painter = rememberVectorPainter(image = screen.icon),
                                contentDescription = stringResource(id = screen.screenNameId)
                            )
                        },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                setUpNavigationOptions(navController)
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Translation.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Translation.route) {
                TranslationScreen(navController)
            }
            composable(Screen.Saved.route) {
                SavedScreen(navController)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(navController)
            }
        }
    }
}

private fun NavOptionsBuilder.setUpNavigationOptions(navController: NavHostController) {
    popUpTo(navController.graph.findStartDestination().id) {
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}

@Composable
private fun getScreenList() = listOf(
    Screen.Translation,
    Screen.Saved,
    Screen.Settings
)

package com.luisfagundes.parrotlingo.navigation

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.luisfagundes.translation.presentation.TranslationViewModel

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
                val viewModel = hiltViewModel<TranslationViewModel>()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                TranslationScreen(
                    uiState = uiState,
                    onTranslateText = viewModel::translateWord,
                    onGetFullLanguageName = viewModel::getLanguageDisplayName,
                )
            }
            composable(Screen.Saved.route) {
                SavedScreen()
            }
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}

private fun NavOptionsBuilder.setUpNavigationOptions(navController: NavHostController) {
    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
    launchSingleTop = true
    restoreState = true
}

@Composable
private fun getScreenList() = listOf(
    Screen.Translation,
    Screen.Saved,
    Screen.Settings
)

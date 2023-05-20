package com.luisfagundes.parrotlingo.navigation.routes

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.luisfagundes.framework.utils.commonNavigationOptions
import com.luisfagundes.parrotlingo.navigation.BottomBarScreen
import com.luisfagundes.settings.SettingsScreen
import com.luisfagundes.settings.SettingsViewModel
import com.luisfagundes.settings.applanguages.AppLanguageViewModel
import com.luisfagundes.settings.applanguages.AppLanguagesScreen

fun NavGraphBuilder.settingsRoute(navHostController: NavHostController) {
    composable(route = BottomBarScreen.Settings.route) {
        val viewModel = hiltViewModel<SettingsViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        SettingsScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onNavigateToLanguages = {
                navHostController.navigate(
                    route = SettingsRoutes.Languages.route,
                ) {
                    commonNavigationOptions(navHostController)
                }
            },
        )
    }

    composable(route = SettingsRoutes.Languages.route) {
        val viewModel = hiltViewModel<AppLanguageViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        AppLanguagesScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onLanguageClick = { navHostController.popBackStack() },
        )
    }
}

enum class SettingsRoutes(val route: String) {
    Languages("settings/languages"),
}

package com.luisfagundes.parrotlingo.navigation.routes

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.luisfagundes.framework.utils.commonNavigationOptions
import com.luisfagundes.parrotlingo.navigation.BottomBarScreen
import com.luisfagundes.settings.SettingsNavigationEvent
import com.luisfagundes.settings.SettingsScreen
import com.luisfagundes.settings.SettingsViewModel
import com.luisfagundes.settings.about.AboutScreen
import com.luisfagundes.settings.applanguages.AppLanguageViewModel
import com.luisfagundes.settings.applanguages.AppLanguagesScreen

fun NavGraphBuilder.settingsRoute(navHostController: NavHostController) {
    composable(route = BottomBarScreen.Settings.route) {
        val viewModel = hiltViewModel<SettingsViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        SettingsScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onNavigateEvent = { event ->
                handleSettingsNavigationEvent(event, navHostController)
            },
        )
    }
    composable(route = SettingsRoutes.Languages.route) {
        val viewModel = hiltViewModel<AppLanguageViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        AppLanguagesScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onBackPressed = { navHostController.popBackStack() },
        )
    }
    composable(route = SettingsRoutes.About.route) {
        AboutScreen()
    }
}

private fun handleSettingsNavigationEvent(
    event: SettingsNavigationEvent,
    navHostController: NavHostController,
) {
    when (event) {
        is SettingsNavigationEvent.NavigateToLanguages -> {
            navHostController.navigate(
                route = SettingsRoutes.Languages.route,
            ) { commonNavigationOptions() }
        }

        is SettingsNavigationEvent.NavigateToAbout -> {
            navHostController.navigate(
                route = SettingsRoutes.About.route,
            ) { commonNavigationOptions() }
        }
    }
}

enum class SettingsRoutes(val route: String) {
    Languages("settings/languages"),
    About("settings/about"),
}

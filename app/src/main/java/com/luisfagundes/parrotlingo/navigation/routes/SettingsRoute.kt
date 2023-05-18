package com.luisfagundes.parrotlingo.navigation.routes

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.luisfagundes.parrotlingo.navigation.BottomBarScreen
import com.luisfagundes.settings.SettingsScreen
import com.luisfagundes.settings.SettingsViewModel

fun NavGraphBuilder.settingsRoute() {
    composable(route = BottomBarScreen.Settings.route) {
        val viewModel = hiltViewModel<SettingsViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        SettingsScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
        )
    }
}

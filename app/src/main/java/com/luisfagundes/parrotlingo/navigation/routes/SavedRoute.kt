package com.luisfagundes.parrotlingo.navigation.routes

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.luisfagundes.parrotlingo.navigation.BottomBarScreen
import com.luisfagundes.saved.SavedScreen
import com.luisfagundes.saved.SavedViewModel

fun NavGraphBuilder.savedRoute() {
    composable(route = BottomBarScreen.Saved.route) {
        val viewModel = hiltViewModel<SavedViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        SavedScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
        )
    }
}

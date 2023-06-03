package com.luisfagundes.parrotlingo.navigation.routes

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.luisfagundes.framework.utils.commonNavigationOptions
import com.luisfagundes.framework.utils.doNothing
import com.luisfagundes.parrotlingo.navigation.BottomBarScreen
import com.luisfagundes.translation.TranslationScreen
import com.luisfagundes.translation.presentation.TranslationEvent
import com.luisfagundes.translation.presentation.TranslationViewModel

fun NavGraphBuilder.translationRoute(navController: NavHostController) {
    composable(
        route = BottomBarScreen.Translation.route,
    ) {
        val viewModel = hiltViewModel<TranslationViewModel>()
        val uiState by viewModel.wordState.collectAsStateWithLifecycle()

        TranslationScreen(
            uiState = uiState,
            onEvent = {
                viewModel.onEvent(it)
                handleNavigation(navController, it)
            },
        )
    }
}

private fun handleNavigation(
    navController: NavHostController,
    event: TranslationEvent,
) {
    when (event) {
        is TranslationEvent.OnLanguageClicked -> navController.navigate(
            "languageListScreen/${event.isSourceLanguage}",
        ) {
            commonNavigationOptions()
        }
        else -> doNothing()
    }
}

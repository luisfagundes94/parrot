package com.luisfagundes.parrotlingo.navigation.routes

import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luisfagundes.commons_util.RouteParams.IS_SOURCE_LANGUAGE
import com.luisfagundes.commons_util.RouteParams.LANGUAGE_ID
import com.luisfagundes.framework.extension.empty
import com.luisfagundes.framework.utils.commonNavigationOptions
import com.luisfagundes.framework.utils.doNothing
import com.luisfagundes.parrotlingo.navigation.BottomBarScreen
import com.luisfagundes.translation.TranslationScreen
import com.luisfagundes.translation.presentation.TranslationUIEvent
import com.luisfagundes.translation.presentation.TranslationViewModel

fun NavGraphBuilder.translationRoute(navController: NavHostController) {
    composable(
        route = BottomBarScreen.Translation.route,
        arguments = listOf(
            navArgument(LANGUAGE_ID) {
                type = NavType.StringType
                defaultValue = String.empty()
            },
            navArgument(IS_SOURCE_LANGUAGE) {
                type = NavType.BoolType
                defaultValue = true
            }
        )
    ) { backStackEntry ->
        val factory = HiltViewModelFactory(LocalContext.current, backStackEntry)
        val viewModel: TranslationViewModel = viewModel(factory = factory)
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        TranslationScreen(
            uiState = uiState,
            onEvent = {
                viewModel.onEvent(it)
                handleNavigation(navController, it)
            }
        )
    }
}

private fun handleNavigation(
    navController: NavHostController,
    event: TranslationUIEvent
) {
    when (event) {
        is TranslationUIEvent.OnLanguageClicked -> navController.navigate(
            "languageListScreen/${event.isSourceLanguage}"
        ) {
            commonNavigationOptions(navController)
        }
        else -> doNothing()
    }
}
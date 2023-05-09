package com.luisfagundes.parrotlingo.navigation.routes

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luisfagundes.commons_util.RouteParams.IS_SOURCE_LANGUAGE
import com.luisfagundes.framework.utils.commonNavigationOptions
import com.luisfagundes.framework.utils.doNothing
import com.luisfagundes.languages.LanguageListEvent
import com.luisfagundes.languages.LanguageListScreen
import com.luisfagundes.languages.LanguageListViewModel
import com.luisfagundes.parrotlingo.navigation.BottomBarScreen

fun NavGraphBuilder.registerLanguageListRoute(navController: NavHostController) {
    composable(
        route = "languageListScreen/$IS_SOURCE_LANGUAGE",
        arguments = listOf(
            navArgument(IS_SOURCE_LANGUAGE) {
                defaultValue = false
            }
        )
    ) {
        val viewModel = hiltViewModel<LanguageListViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LanguageListScreen(
            uiState = uiState,
            onEvent = { event ->
                viewModel.onEvent(event)
                handleNavigation(
                    event = event,
                    navController = navController
                )
            }
        )
    }
}

fun handleNavigation(
    event: LanguageListEvent,
    navController: NavHostController,
) {
    when (event) {
        is LanguageListEvent.OnBackPressed -> navController.popBackStack()
        is LanguageListEvent.OnLanguageClicked -> navController.navigate(
            BottomBarScreen.Translation.addLanguage(
                id = event.id,
                isSourceLanguage = event.isSourceLanguage
            )
        ) {
            commonNavigationOptions(navController)
        }
        else -> doNothing()
    }
}

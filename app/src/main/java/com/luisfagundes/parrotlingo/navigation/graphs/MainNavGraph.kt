package com.luisfagundes.parrotlingo.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.luisfagundes.domain.models.Country
import com.luisfagundes.languages.LanguageListScreen
import com.luisfagundes.parrotlingo.navigation.BottomBarScreen
import com.luisfagundes.saved.SavedScreen
import com.luisfagundes.settings.SettingsScreen
import com.luisfagundes.translation.TranslationScreen
import com.luisfagundes.translation.presentation.TranslationViewModel

@Composable
fun MainNavGraph(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.MAIN,
        startDestination = BottomBarScreen.Translation.route
    ) {
        composable(route = BottomBarScreen.Translation.route) {
            val viewModel = hiltViewModel<TranslationViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            TranslationScreen(
                uiState = uiState,
                onEvent = viewModel::onEvent,
                onLanguageClicked = {
                    navController.navigate(Graph.LANGUAGE_LIST) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = BottomBarScreen.Saved.route) {
            SavedScreen()
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen()
        }
        languageListNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.languageListNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.LANGUAGE_LIST,
        startDestination = "languageListScreen"
    ) {
        composable(route = "languageListScreen") {
            val countries = listOf(
                Country(
                    name = "United States",
                    code = "US",
                    flagUrl = "https://flagsapi.com/US/flat/64.png",
                    languages = listOf("English")
                ),
                Country(
                    name = "Brazil",
                    code = "BR",
                    flagUrl = "https://flagsapi.com/BR/flat/64.png",
                    languages = listOf("Portuguese")
                ),
            )
            LanguageListScreen(
                countries = countries,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

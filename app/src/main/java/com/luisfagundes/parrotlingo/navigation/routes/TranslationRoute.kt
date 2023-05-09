package com.luisfagundes.parrotlingo.navigation.routes

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.luisfagundes.framework.utils.commonNavigationOptions
import com.luisfagundes.parrotlingo.navigation.BottomBarScreen
import com.luisfagundes.translation.TranslationScreen
import com.luisfagundes.translation.presentation.TranslationViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow

fun NavGraphBuilder.registerTranslationRoute(navController: NavHostController) {
    composable(route = BottomBarScreen.Translation.route) {
        val viewModel = hiltViewModel<TranslationViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        TranslationScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent
        )

        LaunchedEffect(viewModel) {
            viewModel.navigationEventChannel.consumeAsFlow().collectLatest { event ->
                navController.navigate("languageListScreen/${event.isSourceLanguage}") {
                    commonNavigationOptions(navController)
                }
            }
        }
    }
}
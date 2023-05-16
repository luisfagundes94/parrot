package com.luisfagundes.parrotlingo.navigation.routes

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luisfagundes.commonsUtil.RouteParams.IS_SOURCE_LANGUAGE
import com.luisfagundes.framework.utils.doNothing
import com.luisfagundes.languages.LanguageListEvent
import com.luisfagundes.languages.LanguageListScreen
import com.luisfagundes.languages.LanguageListViewModel

fun NavGraphBuilder.languageListRoute(navController: NavHostController) {
  composable(
    route = "languageListScreen/{$IS_SOURCE_LANGUAGE}",
    arguments = listOf(
      navArgument(IS_SOURCE_LANGUAGE) {
        defaultValue = true
      },
    ),
  ) {
    val viewModel = hiltViewModel<LanguageListViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LanguageListScreen(
      uiState = uiState,
      onEvent = { event ->
        viewModel.onEvent(event)
        handleNavigation(
          event = event,
          navController = navController,
        )
      },
    )
  }
}

fun handleNavigation(
  event: LanguageListEvent,
  navController: NavHostController,
) {
  navController.run {
    when (event) {
      is LanguageListEvent.OnBackPressed -> popBackStack()
      is LanguageListEvent.OnLanguageClicked -> popBackStack()
      else -> doNothing()
    }
  }
}

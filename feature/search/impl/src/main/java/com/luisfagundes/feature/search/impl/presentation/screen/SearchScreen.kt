package com.luisfagundes.feature.search.impl.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.core.designsystem.ui.theme.ParrotTheme
import com.luisfagundes.core.designsystem.ui.theme.spacing
import com.luisfagundes.feature.search.impl.R
import com.luisfagundes.feature.search.impl.domain.model.WordEntry
import com.luisfagundes.feature.search.impl.presentation.components.RecentlyLookedUpWords
import com.luisfagundes.feature.search.impl.presentation.components.WordSearchBar
import com.luisfagundes.feature.search.impl.presentation.event.SearchEvent
import com.luisfagundes.feature.search.impl.presentation.state.SearchState
import com.luisfagundes.feature.search.impl.presentation.viewmodel.SearchViewModel

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.dispatchEvent(SearchEvent.OnGetRecentlyLookedUpWords)
    }

    SearchScreen(
        state = state,
        sendEvent = viewModel::dispatchEvent
    )
}

@Composable
private fun SearchScreen(
    state: SearchState,
    sendEvent: (SearchEvent) -> Unit,
) {
    when (state) {
        is SearchState.Error -> SearchErrorContent(
            message = state.message,
            sendEvent = sendEvent,
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.default)
        )
        is SearchState.Content -> SearchContent(
            suggestions = state.suggestions,
            recentlyLookedUpWords = state.recentlyLookedUpWords,
            sendEvent = sendEvent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.default)
        )
    }
}

@Composable
private fun SearchErrorContent(
    message: String,
    sendEvent: (SearchEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = { sendEvent(SearchEvent.OnGetRecentlyLookedUpWords) }
        ) {
            Text(stringResource(R.string.try_again))
        }
    }
}

@Composable
private fun SearchContent(
    suggestions: List<String>,
    recentlyLookedUpWords: List<WordEntry>,
    sendEvent: (SearchEvent) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        WordSearchBar(
            suggestions = suggestions,
            onQueryChange = { sendEvent(SearchEvent.OnQueryChange(it)) },
            onSearch = { sendEvent(SearchEvent.OnSearchClick(it)) }
        )
        if (recentlyLookedUpWords.isNotEmpty()) {
            RecentlyLookedUpWords(
                words = recentlyLookedUpWords,
                modifier = Modifier.fillMaxWidth().padding(vertical = MaterialTheme.spacing.default)
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun SearchScreenPreview() {
    ParrotTheme {
        Surface(Modifier.fillMaxSize()) {
            SearchScreen(
                state = SearchState.Content(),
                sendEvent = {}
            )
        }
    }
}

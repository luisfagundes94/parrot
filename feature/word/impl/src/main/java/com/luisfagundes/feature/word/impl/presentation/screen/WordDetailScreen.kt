package com.luisfagundes.feature.word.impl.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.core.common.presentation.arch.compose.CollectUiEffects
import com.luisfagundes.core.designsystem.ui.theme.ParrotTheme
import com.luisfagundes.core.designsystem.ui.theme.spacing
import com.luisfagundes.feature.word.impl.R
import com.luisfagundes.feature.word.impl.presentation.components.MeaningSection
import com.luisfagundes.feature.word.impl.presentation.components.WordDetailTopBar
import com.luisfagundes.feature.word.impl.presentation.components.WordHeader
import com.luisfagundes.feature.word.impl.presentation.effect.WordDetailEffect
import com.luisfagundes.feature.word.impl.presentation.event.WordDetailEvent
import com.luisfagundes.feature.word.impl.presentation.state.MeaningUi
import com.luisfagundes.feature.word.impl.presentation.state.WordDetailState
import com.luisfagundes.feature.word.impl.presentation.viewmodel.WordDetailViewModel

@Composable
internal fun WordDetailScreen(
    word: String,
    onNavigateBack: () -> Unit,
    viewModel: WordDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(word) {
        viewModel.dispatchEvent(WordDetailEvent.OnLoad(word))
    }

    CollectUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is WordDetailEffect.NavigateBack -> onNavigateBack()
        }
    }

    WordDetailScreen(
        state = state,
        word = word,
        sendEvent = viewModel::dispatchEvent
    )
}

@Composable
private fun WordDetailScreen(
    state: WordDetailState,
    word: String,
    sendEvent: (WordDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        WordDetailTopBar(
            isBookmarked = (state as? WordDetailState.Content)?.isBookmarked,
            onBackClick = { sendEvent(WordDetailEvent.OnBackClick) },
            onBookmarkClick = { sendEvent(WordDetailEvent.OnBookmarkClick) }
        )
        when (state) {
            is WordDetailState.Loading -> WordDetailLoading(
                modifier = Modifier.weight(1f).fillMaxWidth()
            )
            is WordDetailState.Error -> WordDetailError(
                message = state.message,
                onRetryClick = { sendEvent(WordDetailEvent.OnLoad(word)) },
                modifier = Modifier.weight(1f).fillMaxWidth()
            )
            is WordDetailState.Content -> WordDetailContent(
                state = state,
                sendEvent = sendEvent,
                modifier = Modifier.weight(1f).fillMaxWidth()
            )
        }
    }
}

@Composable
private fun WordDetailLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun WordDetailError(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(MaterialTheme.spacing.default),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center
        )
        Button(onClick = onRetryClick) {
            Text(stringResource(R.string.try_again))
        }
    }
}

@Composable
private fun WordDetailContent(
    state: WordDetailState.Content,
    sendEvent: (WordDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.default),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
        ) {
            WordHeader(
                word = state.word,
                phonetic = state.phonetic,
                isPlayingAudio = state.isPlayingAudio,
                onPlayAudioClick = { sendEvent(WordDetailEvent.OnPlayAudioClick) }
            )
            if (state.meanings.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_meanings),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            state.meanings.forEach { meaning ->
                MeaningSection(meaning = meaning, word = state.word)
            }
        }
        Button(
            onClick = { sendEvent(WordDetailEvent.OnSetReminderClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.default)
                .heightIn(min = 56.dp)
        ) {
            Text(
                text = stringResource(R.string.set_a_reminder),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun WordDetailScreenPreview() {
    ParrotTheme {
        Surface(Modifier.fillMaxSize()) {
            WordDetailScreen(
                state = WordDetailState.Content(
                    word = "serendipity",
                    phonetic = "/ˌser.ənˈdɪp.ɪ.ti/",
                    meanings = listOf(
                        MeaningUi(
                            partOfSpeech = "noun",
                            definition = "The chance discovery of pleasant or valuable things you were not looking for.",
                            examples = listOf(
                                "Finding that out-of-print novel at a flea market was pure serendipity.",
                                "We met by serendipity on a delayed train, and we have been friends since."
                            )
                        )
                    )
                ),
                word = "serendipity",
                sendEvent = {}
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun WordDetailErrorPreview() {
    ParrotTheme {
        Surface(Modifier.fillMaxSize()) {
            WordDetailScreen(
                state = WordDetailState.Error("No definition found for \"asdfgh\""),
                word = "asdfgh",
                sendEvent = {}
            )
        }
    }
}

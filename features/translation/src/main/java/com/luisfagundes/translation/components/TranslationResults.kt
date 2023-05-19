package com.luisfagundes.translation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisfagundes.domain.models.ScheduleData
import com.luisfagundes.domain.models.Word
import com.luisfagundes.framework.composeComponents.LoadingView
import com.luisfagundes.framework.composeComponents.WarningView
import com.luisfagundes.framework.extension.showToast
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R
import com.luisfagundes.translation.presentation.TranslationUiState

@Composable
fun TranslationResults(
    uiState: TranslationUiState,
    onSaveWord: (ScheduleData, Word) -> Unit,
) {
    val modifier = Modifier.padding(vertical = MaterialTheme.spacing.default)

    showToast(
        shouldShow = uiState.shouldShowSavedWordToast,
        message = stringResource(R.string.word_saved),
    )

    when {
        uiState.hasError -> WarningView(
            modifier = modifier,
            title = stringResource(R.string.warning_error),
            animationId = com.luisfagundes.theme.R.raw.warning,
        )

        uiState.isEmpty -> WarningView(
            modifier = modifier,
            title = stringResource(R.string.warning_empty),
            animationId = com.luisfagundes.theme.R.raw.warning,
        )

        uiState.isLoading -> LoadingView(
            modifier = modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.default),
        )

        uiState.wordList.isNotEmpty() -> WordTranslationListContent(
            words = uiState.wordList,
            onSaveWord = { scheduleData, word ->
                onSaveWord(scheduleData, word)
            },
            areExamplesEmpty = uiState.areExamplesEmpty,
        )
    }
}

@Composable
private fun WordTranslationListContent(
    words: List<Word>,
    onSaveWord: (ScheduleData, Word) -> Unit,
    areExamplesEmpty: Boolean,
) {
    val modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall)

    Spacer(modifier = modifier)
    OtherTranslations(
        words = words,
        onSaveWord = onSaveWord,
    )
    Spacer(modifier = modifier)
    ExampleListContent(
        words = words,
        areExamplesEmpty = areExamplesEmpty,
    )
}

package com.luisfagundes.translation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.luisfagundes.domain.models.Translation
import com.luisfagundes.domain.models.Word
import com.luisfagundes.framework.components.ErrorView
import com.luisfagundes.framework.components.LoadingView
import com.luisfagundes.framework.extension.capitalize
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R
import com.luisfagundes.translation.presentation.TranslationUiState

@Composable
fun TranslationResults(
    uiState: TranslationUiState
) {
    val modifier = Modifier.padding(vertical = MaterialTheme.spacing.default)

    when {
        uiState.hasError -> ErrorView(
            modifier = modifier,
            message = stringResource(R.string.warning_error),
            animationId = R.raw.warning
        )

        uiState.isEmpty -> ErrorView(
            modifier = modifier,
            message = stringResource(R.string.warning_empty),
            animationId = R.raw.warning
        )

        uiState.isLoading -> LoadingView(
            modifier = modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.default)
        )

        uiState.wordList.isNotEmpty() -> SuccessView(uiState.wordList)
    }
}

@Composable
private fun SuccessView(
    words: List<Word>
) {
    var isContainerEmpty by remember { mutableStateOf(false) }

    Spacer(
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall)
    )
    ContainerBox(isEmpty = isContainerEmpty) {
        OtherTranslations(
            words = words,
            isEmpty = { isContainerEmpty = it }
        )
    }
    Spacer(
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall)
    )
    ContainerBox(isEmpty = isContainerEmpty) {
        Definitions(
            words = words,
            isEmpty = { isContainerEmpty = it }
        )
    }
}

@Composable
private fun ContainerBox(
    isEmpty: Boolean,
    container: @Composable () -> Unit
) {
    if (isEmpty) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.small
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = MaterialTheme.shapes.small
            )
            .padding(MaterialTheme.spacing.small)
    ) {
        container.invoke()
    }
}

@Composable
private fun OtherTranslations(
    words: List<Word>,
    isEmpty: (Boolean) -> Unit
) {
    Text(
        text = words.first().translations.first().text,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
    )
    Spacer(
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall)
    )
    Text(
        text = "Other translations",
        style = MaterialTheme.typography.titleMedium,
    )
    words.forEach { word ->
        word.translations.forEach { translation ->
            TranslationItem(
                translation = translation,
                isEmpty = isEmpty,
                isFeatured = true
            )
        }
    }
}

@Composable
private fun Definitions(
    words: List<Word>,
    isEmpty: (Boolean) -> Unit
) {
    Text(
        text = getDefinitionOfWordText(words.first()),
        style = MaterialTheme.typography.titleMedium,
    )
    words.forEach { word ->
        word.translations.forEach { translation ->
            TranslationItem(
                translation = translation,
                isEmpty = isEmpty,
                isFeatured = false
            )
        }
    }
}

@Composable
private fun getDefinitionOfWordText(word: Word): AnnotatedString {
    val annotatedString = buildAnnotatedString {
        append("Definitions of ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(word.text)
        }
    }
    return annotatedString
}

@Composable
private fun TranslationItem(
    translation: Translation,
    isEmpty: (Boolean) -> Unit,
    isFeatured: Boolean
) {
    isEmpty(translation.examples.isEmpty())

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall)
        )
        Text(
            text = translation.wordType.capitalize(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        if (isFeatured.not()) Examples(translation.examples)
    }
    if (isFeatured) Text(translation.text)
}
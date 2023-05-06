package com.luisfagundes.translation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
    val modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall)

    Spacer(modifier = modifier)
    OtherTranslations(words = words)
    Spacer(modifier = modifier)
    Definitions(words = words)
}

@Composable
private fun ContainerBox(
    container: @Composable () -> Unit
) {
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
    words: List<Word>
) {
    if (words.all { it.translations.isEmpty() }) return

    ContainerBox {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = words.first().translations.first().text,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.scale(1.1f),
                imageVector = Icons.Filled.BookmarkAdd,
                contentDescription = stringResource(R.string.desc_bookmark_word)
            )
        }
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
                    isFeatured = true
                )
            }
        }
    }
}

@Composable
private fun Definitions(
    words: List<Word>
) {
    if (words.all { it.translations.isEmpty() }) return

    ContainerBox {
        Text(
            text = getExamplesOf(words.first()),
            style = MaterialTheme.typography.titleMedium,
        )
        words.forEach { word ->
            word.translations.forEach { translation ->
                TranslationItem(
                    translation = translation,
                    isFeatured = false
                )
            }
        }
    }
}

@Composable
private fun getExamplesOf(word: Word): AnnotatedString {
    val annotatedString = buildAnnotatedString {
        append(stringResource(R.string.title_examples_of))
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(word.text)
        }
    }
    return annotatedString
}

@Composable
private fun TranslationItem(
    translation: Translation,
    isFeatured: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (isFeatured.not() and translation.examples.isEmpty()) return@Column
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
        else Text(translation.text)
    }
}
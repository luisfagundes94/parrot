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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.luisfagundes.domain.models.Example
import com.luisfagundes.domain.models.Translation
import com.luisfagundes.domain.models.Word
import com.luisfagundes.framework.components.ErrorView
import com.luisfagundes.framework.components.LoadingView
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R
import com.luisfagundes.translation.presentation.TranslationUiState
import java.util.Locale

@Composable
fun TranslationResult(
    uiState: TranslationUiState
) {
    when {
        uiState.hasError -> ErrorView(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.default),
            message = stringResource(R.string.error_message),
            animationId = R.raw.warning
        )

        uiState.isEmpty -> Text(text = "empty")
        uiState.isLoading -> LoadingView(
            modifier = Modifier
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
    Spacer(
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall)
    )
    ContainerBox {
        OtherTranslations(words = words)
    }
    Spacer(
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall)
    )
    ContainerBox {
        Definitions(words = words)
    }
}

@Composable
private fun ContainerBox(container: @Composable () -> Unit) {
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
private fun OtherTranslations(words: List<Word>) {
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
            FeaturedTranslationItem(translation)
        }
    }
}

@Composable
private fun Definitions(words: List<Word>) {
    Text(
        text = getDefinitionOfWordText(words.first()),
        style = MaterialTheme.typography.titleMedium,
    )
    words.forEach { word ->
        word.translations.forEach { translation ->
            NotFeaturedTranslationItem(translation)
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
private fun FeaturedTranslationItem(
    translation: Translation
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall)
        )
        Text(
            text = translation.wordType.capitalize(Locale.getDefault()),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
    Text(translation.text)
}

@Composable
private fun NotFeaturedTranslationItem(
    translation: Translation
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (translation.examples.isNotEmpty()) {
            Spacer(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall)
            )
            Text(
                text = translation.wordType.capitalize(Locale.getDefault()),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Examples(translation.examples)
        }
    }
}


@Composable
private fun Examples(examples: List<Example>) {
    Spacer(
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall)
    )
    examples.forEach { example ->
        Text(text = example.destinationLanguage)
        Spacer(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.extraSmall)
        )
        Text(
            text = "\"${example.sourceLanguage}\"",
            fontStyle = FontStyle.Italic
        )
    }
}
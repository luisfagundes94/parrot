package com.luisfagundes.translation.components

import androidx.compose.foundation.layout.Spacer
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
import com.luisfagundes.domain.models.Example
import com.luisfagundes.domain.models.Word
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R

@Composable
fun ExampleListContent(
    words: List<Word>,
    areExamplesEmpty: Boolean,
) {
    if (areExamplesEmpty) return

    ContainerBox {
        Text(
            text = getExamplesOf(words.first()),
            style = MaterialTheme.typography.titleMedium,
        )
        words.forEach { word ->
            word.translations.forEach { translation ->
                TranslationItem(
                    translation = translation,
                    isFeatured = false,
                )
            }
        }
    }
}

@Composable
fun ExampleList(examples: List<Example>) {
    Spacer(
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall),
    )
    examples.forEach { example ->
        Text(text = example.destinationLanguage)
        Spacer(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.extraSmall),
        )
        Text(
            text = "\"${example.sourceLanguage}\"",
            fontStyle = FontStyle.Italic,
        )
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

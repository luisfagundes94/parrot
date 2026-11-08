package com.luisfagundes.feature.word.impl.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.luisfagundes.core.designsystem.ui.theme.ParrotTheme
import com.luisfagundes.core.designsystem.ui.theme.spacing
import com.luisfagundes.feature.word.impl.R
import com.luisfagundes.feature.word.impl.presentation.state.MeaningUi

@Composable
internal fun MeaningSection(
    meaning: MeaningUi,
    word: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        if (meaning.partOfSpeech.isNotBlank()) {
            PartOfSpeechChip(partOfSpeech = meaning.partOfSpeech)
        }
        SectionLabel(text = stringResource(R.string.meaning))
        Text(
            text = meaning.definition,
            style = MaterialTheme.typography.titleMedium
        )
        if (meaning.examples.isNotEmpty()) {
            SectionLabel(
                text = stringResource(R.string.in_sentences),
                modifier = Modifier.padding(top = MaterialTheme.spacing.small)
            )
            meaning.examples.forEach { example ->
                SentenceCard(sentence = example, word = word)
            }
        }
    }
}

@Composable
private fun SectionLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.semantics { heading() }
    )
}

@Composable
@PreviewLightDark
private fun MeaningSectionPreview() {
    ParrotTheme {
        Surface {
            MeaningSection(
                meaning = MeaningUi(
                    partOfSpeech = "noun",
                    definition = "The chance discovery of pleasant or valuable things you were not looking for.",
                    examples = listOf(
                        "Finding that out-of-print novel at a flea market was pure serendipity.",
                        "We met by serendipity on a delayed train, and we have been friends since."
                    )
                ),
                word = "serendipity",
                modifier = Modifier.padding(MaterialTheme.spacing.default)
            )
        }
    }
}

package com.luisfagundes.feature.search.impl.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.luisfagundes.core.designsystem.ui.theme.ParrotTheme
import com.luisfagundes.core.designsystem.ui.theme.spacing
import com.luisfagundes.feature.search.impl.R
import com.luisfagundes.core.dictionary.domain.model.Definition
import com.luisfagundes.core.dictionary.domain.model.Meaning
import com.luisfagundes.core.dictionary.domain.model.WordEntry

@Composable
internal fun RecentlyLookedUpWords(
    words: List<WordEntry>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.recently_looked_up),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(
            modifier = Modifier.height(MaterialTheme.spacing.small)
        )
        LazyColumn {
            itemsIndexed(words) { index, word ->
                WordCard(
                    wordEntry = word,
                    shape = cardShape(index = index, lastIndex = words.lastIndex),
                    showDivider = index != words.lastIndex,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun cardShape(index: Int, lastIndex: Int): Shape {
    val radius = 16.dp
    val top = if (index == 0) radius else 0.dp
    val bottom = if (index == lastIndex) radius else 0.dp
    return RoundedCornerShape(
        topStart = top,
        topEnd = top,
        bottomStart = bottom,
        bottomEnd = bottom
    )
}

@Composable
private fun WordCard(
    wordEntry: WordEntry,
    shape: Shape,
    showDivider: Boolean,
    modifier: Modifier = Modifier
) {
    val word = wordEntry.word
    val meaning = wordEntry.meanings.firstOrNull()
    val partOfSpeech = meaning?.partOfSpeech.orEmpty()
    val definition = meaning?.definitions?.firstOrNull()
    val definitionText = definition?.text.orEmpty()

    Card(
        modifier = modifier,
        shape = shape,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.default),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = word,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    if (partOfSpeech.isNotBlank() && definitionText.isNotBlank()) {
                        Text(
                            text = "$partOfSpeech · $definitionText",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.navigate_to_word_details),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (showDivider) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun RecentlyLookedUpWordsPreview() {
    val serendipityMeanings = listOf(
        Meaning(
            partOfSpeech = "noun",
            definitions = listOf(
                Definition("a happy discovery made by chance", example = null)
            ),
            synonyms = emptyList(),
            antonyms = emptyList()
        )
    )
    val resilientMeanings = listOf(
        Meaning(
            partOfSpeech = "adj",
            definitions = listOf(
                Definition("able to recover quickly", example = null)
            ),
            synonyms = emptyList(),
            antonyms = emptyList()
        )
    )
    val ephemeralMeanings = listOf(
        Meaning(
            partOfSpeech = "adj",
            definitions = listOf(
                Definition("lasting a very short time", example = null)
            ),
            synonyms = emptyList(),
            antonyms = emptyList()
        )
    )
    val words = listOf(
        WordEntry("serendipity", null, null, serendipityMeanings),
        WordEntry("resilient", null, null, resilientMeanings),
        WordEntry("ephemeral", null, null, ephemeralMeanings),
    )
    ParrotTheme {
        Surface {
            RecentlyLookedUpWords(
                words = words,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.default)
            )
        }
    }
}
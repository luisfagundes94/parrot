package com.luisfagundes.feature.word.impl.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.luisfagundes.core.designsystem.ui.theme.ParrotTheme
import com.luisfagundes.core.designsystem.ui.theme.spacing
import com.luisfagundes.feature.word.impl.presentation.utility.highlightWord

@Composable
internal fun SentenceCard(
    sentence: String,
    word: String,
    modifier: Modifier = Modifier
) {
    val highlight = SpanStyle(
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold
    )
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Text(
            text = highlightWord(sentence, word, highlight),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(MaterialTheme.spacing.default)
        )
    }
}

@Composable
@PreviewLightDark
private fun SentenceCardPreview() {
    ParrotTheme {
        Surface {
            SentenceCard(
                sentence = "Finding that out-of-print novel at a flea market was pure serendipity.",
                word = "serendipity",
                modifier = Modifier.padding(MaterialTheme.spacing.default)
            )
        }
    }
}

package com.luisfagundes.feature.word.impl.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.luisfagundes.core.designsystem.ui.theme.ParrotTheme
import com.luisfagundes.core.designsystem.ui.theme.spacing

@Composable
internal fun PartOfSpeechChip(
    partOfSpeech: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Text(
            text = partOfSpeech,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.spacing.content,
                vertical = MaterialTheme.spacing.verySmall
            )
        )
    }
}

@Composable
@PreviewLightDark
private fun PartOfSpeechChipPreview() {
    ParrotTheme {
        Surface {
            PartOfSpeechChip(partOfSpeech = "noun")
        }
    }
}

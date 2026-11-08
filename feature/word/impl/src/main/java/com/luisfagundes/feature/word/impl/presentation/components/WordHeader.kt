package com.luisfagundes.feature.word.impl.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.luisfagundes.core.designsystem.ui.theme.ParrotTheme
import com.luisfagundes.core.designsystem.ui.theme.spacing
import com.luisfagundes.feature.word.impl.R

@Composable
internal fun WordHeader(
    word: String,
    phonetic: String?,
    isPlayingAudio: Boolean,
    onPlayAudioClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = word,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.semantics { heading() }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.content)
        ) {
            if (phonetic != null) {
                Text(
                    text = phonetic,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            // The word is pronounced by text to speech, so this is available for every word.
            FilledTonalIconButton(
                onClick = onPlayAudioClick,
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Icon(
                    imageVector = if (isPlayingAudio) {
                        Icons.Filled.Stop
                    } else {
                        Icons.AutoMirrored.Filled.VolumeUp
                    },
                    contentDescription = stringResource(
                        if (isPlayingAudio) R.string.stop_pronunciation else R.string.play_pronunciation
                    )
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun WordHeaderPreview() {
    ParrotTheme {
        Surface {
            WordHeader(
                word = "serendipity",
                phonetic = "/ˌser.ənˈdɪp.ɪ.ti/",
                isPlayingAudio = false,
                onPlayAudioClick = {}
            )
        }
    }
}

package com.luisfagundes.translation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.luisfagundes.domain.models.ScheduleData
import com.luisfagundes.domain.models.Translation
import com.luisfagundes.domain.models.Word
import com.luisfagundes.framework.extension.capitalize
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R

@Composable
fun OtherTranslations(
    words: List<Word>,
    onSaveWord: (ScheduleData, Word) -> Unit,
) {
    if (words.all { it.translations.isEmpty() }) return

    var openConfirmationBottomSheet by remember { mutableStateOf(false) }

    ConfirmationBottomSheet(
        shouldOpenBottomSheet = openConfirmationBottomSheet,
        onDismiss = { openConfirmationBottomSheet = false },
        onConfirmClick = { scheduleData ->
            onSaveWord(
                scheduleData,
                words.first(),
            )
            openConfirmationBottomSheet = false
        },
    )

    ContainerBox {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = words.first().translations.first().text,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    openConfirmationBottomSheet = !openConfirmationBottomSheet
                },
            ) {
                Icon(
                    modifier = Modifier.scale(1.1f),
                    imageVector = Icons.Default.BookmarkAdd,
                    contentDescription = stringResource(R.string.desc_bookmark_word),
                )
            }
        }
        Spacer(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.verySmall),
        )
        Text(
            text = "Other translations",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(MaterialTheme.spacing.small))
        words.forEach { word ->
            Text(
                text = word.translations.first().wordType.capitalize(),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(MaterialTheme.spacing.verySmall))
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.verySmall),
            ) {
                word.translations.joinToString(", ") { it.text }.let { text ->
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

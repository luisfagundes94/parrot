package com.luisfagundes.translation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImportExport
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.luisfagundes.domain.models.Language
import com.luisfagundes.theme.spacing

@Composable
fun SourceAndTargetLanguage(
    sourceLanguage: Language?,
    targetLanguage: Language?,
    onInvertLanguage: () -> Unit
) {
    if (sourceLanguage == null || targetLanguage == null) return

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.default)
            .padding(horizontal = MaterialTheme.spacing.small)

    ) {
        Image(
            painter = painterResource(id = sourceLanguage.flagId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .size(MaterialTheme.spacing.default)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
        Text(
            text = sourceLanguage.displayName,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            imageVector = Icons.Filled.ImportExport,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(MaterialTheme.spacing.default)
                .rotate(90f)
                .weight(1f)
                .clickable { onInvertLanguage() }
        )
        Image(
            painter = painterResource(id = targetLanguage.flagId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .size(MaterialTheme.spacing.default)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
        Text(
            text = targetLanguage.displayName,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
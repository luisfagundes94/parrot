package com.luisfagundes.translation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.luisfagundes.theme.spacing

@Composable
fun TranslationScreen() {
    Column(
        modifier = Modifier
            .padding(MaterialTheme.spacing.default)
            .fillMaxSize()
    ){
        SourceAndDestinationLanguage(
            sourceFlagId = R.drawable.us,
            destinationFlagId = R.drawable.br
        )
    }
}

@Composable
private fun SourceAndDestinationLanguage(
    sourceFlagId: Int,
    destinationFlagId: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium
            )
            .padding(MaterialTheme.spacing.default)

    ) {
        Image(
            painter = painterResource(id = sourceFlagId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .size(MaterialTheme.spacing.default)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
        Text(
            text = "English",
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Icon(
            imageVector = Icons.Filled.ImportExport,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .size(MaterialTheme.spacing.default)
                .rotate(90f)
                .weight(1f)
        )
        Image(
            painter = painterResource(id = destinationFlagId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .size(MaterialTheme.spacing.default)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
        Text(
            text = "Portuguese",
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

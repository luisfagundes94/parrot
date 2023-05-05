package com.luisfagundes.translation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.luisfagundes.domain.models.Country
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R

@Composable
fun LanguagePair(
    countryPair: Pair<Country, Country>,
    onInvertLanguage: () -> Unit,
    onLanguageClicked: () -> Unit
) {
    val sourceCountry = countryPair.first
    val destCountry = countryPair.second

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.default)
            .padding(horizontal = MaterialTheme.spacing.small)

    ) {
        AsyncImage(
            modifier = Modifier
                .size(24.dp)
                .clickable { onLanguageClicked() },
            model = ImageRequest.Builder(LocalContext.current)
                .data(sourceCountry.flagUrl)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.language),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
        Text(
            text = sourceCountry.languages.first(),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable { onLanguageClicked() }
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
        AsyncImage(
            modifier = Modifier
                .size(24.dp)
                .clickable { onLanguageClicked() },
            model = ImageRequest.Builder(LocalContext.current)
                .data(destCountry.flagUrl)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.language),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
        Text(
            text = destCountry.languages.first(),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable { onLanguageClicked() }
        )
    }
}
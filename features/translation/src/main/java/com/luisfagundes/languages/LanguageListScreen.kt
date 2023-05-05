package com.luisfagundes.languages

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.luisfagundes.domain.models.Country
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R
import getSurfaceContainerLowColor
import getSurfaceHighEmphasis

@Composable
fun LanguageListScreen(
    countries: List<Country>,
    onBack: () -> Unit
) {
    Column {
        TopBar(onBack = onBack)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.default)
        ) {
            items(countries) { country ->
                Language(country = country)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onBack: () -> Unit
) {
    TopAppBar(
        title = { Text("Language List") },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
private fun Language(
    country: Country
) {
    val surfaceElevation = 1.dp
    val surfaceColor = getSurfaceContainerLowColor(
        colorScheme = MaterialTheme.colorScheme
    )
    val onSurfaceHighEmphasis = getSurfaceHighEmphasis(
        colorScheme = MaterialTheme.colorScheme
    )

    country.languages.forEach { language ->
        Surface(
            color = surfaceColor,
            tonalElevation = surfaceElevation,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.verySmall)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(MaterialTheme.spacing.small)
            ) {
                AsyncImage(
                    modifier = Modifier.size(50.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(country.flagUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.language),
                    contentScale = ContentScale.Crop
                )
                Spacer(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.verySmall)
                )
                Text(
                    text = language,
                    style = MaterialTheme.typography.titleLarge,
                    color = onSurfaceHighEmphasis
                )
            }
        }
    }
}

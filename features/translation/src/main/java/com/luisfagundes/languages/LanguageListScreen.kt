package com.luisfagundes.languages

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.luisfagundes.commons_ui.ParrotTopBar
import com.luisfagundes.domain.models.Country
import com.luisfagundes.framework.components.LoadingView
import com.luisfagundes.theme.ParrotLingoTheme
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R
import getSurfaceContainerLowColor
import getSurfaceHighEmphasis

@Composable
fun LanguageListScreen(
    uiState: LanguageListUiState,
    onEvent: (LanguageListEvent) -> Unit,
    onBackPressed: () -> Unit
) {
    LaunchedEffect(key1 = Unit, block = {
        onEvent(LanguageListEvent.GetCountryList)
    })

    when {
        uiState.isLoading -> LoadingView()
        uiState.countries.isNotEmpty() -> LanguageList(
            countries = uiState.countries,
            onBackPressed = onBackPressed
        )
    }
}

@Composable
private fun LanguageList(
    countries: List<Country>,
    onBackPressed: () -> Unit
) {
    Column {
        ParrotTopBar(
            name = stringResource(id = R.string.languages),
            onBack = onBackPressed
        )
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

@Composable
private fun Language(
    country: Country
) {
    val tonalElevation = if (isSystemInDarkTheme()) 1.dp else 0.dp
    val shadowElevation = 10.dp

    country.languages.forEach { language ->
        Surface(
            tonalElevation = tonalElevation,
            shadowElevation = shadowElevation,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.verySmall)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
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
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun LanguagePreview() {
    ParrotLingoTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            val country = Country(
                name = "Brazil",
                code = "BR",
                flagUrl = "https://flagsapi.com/BR/flat/64.png",
                languages = listOf("Portuguese")
            )
            Language(country = country)
        }
    }
}

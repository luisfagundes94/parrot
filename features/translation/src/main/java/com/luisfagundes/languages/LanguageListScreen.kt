package com.luisfagundes.languages

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.luisfagundes.commons_ui.ParrotTopBar
import com.luisfagundes.domain.models.Language
import com.luisfagundes.framework.components.WarningView
import com.luisfagundes.framework.components.LoadingView
import com.luisfagundes.theme.ParrotLingoTheme
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R

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
        uiState.countries.isEmpty() -> WarningView(
            modifier = Modifier.fillMaxSize(),
            message = "No results were found!",
            animationId = R.raw.warning
        )
        uiState.countries.isNotEmpty() -> LanguageList(
            countries = uiState.countries,
            onBackPressed = onBackPressed
        )
    }
}

@Composable
private fun LanguageList(
    countries: List<Language>,
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
                Language(language = country)
            }
        }
    }
}

@Composable
private fun Language(
    language: Language
) {
    Card(
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.elevatedCardColors(),
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
            Text(
                text = language.name,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                modifier = Modifier.padding(start = MaterialTheme.spacing.verySmall),
                text = "(${language.nativeName})",
                style = MaterialTheme.typography.titleMedium,
                fontStyle = FontStyle.Italic,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
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
            val language = Language(
                name = "Brazil",
                nativeName = "Portugues",
                code = "BR",
            )
            Language(language = language)
        }
    }
}

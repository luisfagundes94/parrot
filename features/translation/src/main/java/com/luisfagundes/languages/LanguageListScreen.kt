package com.luisfagundes.languages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import com.luisfagundes.commonsUi.ParrotSearch
import com.luisfagundes.commonsUi.ParrotTopBar
import com.luisfagundes.domain.models.Language
import com.luisfagundes.framework.components.LoadingView
import com.luisfagundes.framework.components.WarningView
import com.luisfagundes.theme.spacing
import com.luisfagundes.translation.R

@Composable
fun LanguageListScreen(
    uiState: LanguageListUiState,
    onEvent: (LanguageListEvent) -> Unit,
) {
    LaunchedEffect(key1 = Unit, block = {
        onEvent(LanguageListEvent.GetLanguageList)
    })

    LanguageListContent(onEvent, uiState)
}

@Composable
private fun LanguageListContent(
    onEvent: (LanguageListEvent) -> Unit,
    uiState: LanguageListUiState,
) {
    ParrotTopBar(
        name = stringResource(R.string.languages),
        onBackPressed = { onEvent(LanguageListEvent.OnBackPressed) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = MaterialTheme.spacing.default),
        ) {
            ParrotSearch(
                modifier = Modifier
                    .fillMaxWidth(),
                searchText = uiState.searchText,
                onValueChange = { onEvent(LanguageListEvent.OnSearchTextChanged(it)) },
            )
            when {
                uiState.isLoading -> LoadingView()
                uiState.languages.isEmpty() -> WarningView(
                    modifier = Modifier.fillMaxSize(),
                    title = stringResource(R.string.empty_languages),
                    animationId = com.luisfagundes.theme.R.raw.warning,
                )

                uiState.languages.isNotEmpty() -> LanguageList(
                    languages = uiState.languages,
                    onEvent = onEvent,
                )
            }
        }
    }
}

@Composable
private fun LanguageList(
    languages: List<Language>,
    onEvent: (LanguageListEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = MaterialTheme.spacing.default),
    ) {
        items(languages) { language ->
            Language(
                language = language,
                onLanguageClicked = {
                    onEvent(LanguageListEvent.OnLanguageClicked(language.id, true))
                },
            )
        }
    }
}

@Composable
private fun Language(
    language: Language,
    onLanguageClicked: (String) -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.elevatedCardColors(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.verySmall)
            .clickable { onLanguageClicked(language.id) },
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(MaterialTheme.spacing.small),
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
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

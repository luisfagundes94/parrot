package com.luisfagundes.feature.search.impl.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.luisfagundes.core.designsystem.ui.theme.ParrotTheme
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.luisfagundes.core.designsystem.ui.theme.spacing
import com.luisfagundes.feature.search.impl.R
import kotlinx.coroutines.launch

@Composable
fun WordSearchBar(
    suggestions: List<String>,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textFieldState = rememberTextFieldState()
    val searchBarState = rememberSearchBarState()
    val scope = rememberCoroutineScope()
    val currentOnQueryChange by rememberUpdatedState(onQueryChange)

    LaunchedEffect(textFieldState) {
        snapshotFlow { textFieldState.text.toString() }
            .collect { currentOnQueryChange(it) }
    }

    val inputField = @Composable {
        SearchBarDefaults.InputField(
            textFieldState = textFieldState,
            searchBarState = searchBarState,
            onSearch = { query ->
                onSearch(query)
                scope.launch { searchBarState.animateToCollapsed() }
            },
            placeholder = { Text(stringResource(R.string.search_a_word)) },
            leadingIcon = {
                if (searchBarState.currentValue == SearchBarValue.Expanded) {
                    IconButton(
                        onClick = { scope.launch { searchBarState.animateToCollapsed() } }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                } else {
                    Icon(Icons.Filled.Search, contentDescription = null)
                }
            },
        )
    }

    SearchBar(
        state = searchBarState,
        inputField = inputField,
        modifier = modifier,
    )

    ExpandedFullScreenSearchBar(
        state = searchBarState,
        inputField = inputField,
    ) {
        LazyColumn {
            itemsIndexed(suggestions) { index, word ->
                if (index > 0) HorizontalDivider()
                ListItem(
                    onClick = {
                        textFieldState.setTextAndPlaceCursorAtEnd(word)
                        onSearch(word)
                        scope.launch { searchBarState.animateToCollapsed() }
                    },
                    shapes = ListItemDefaults.shapes(shape = RectangleShape),
                ) {
                    Text(word)
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun WordSearchBarPreview() {
    ParrotTheme {
        Surface {
            WordSearchBar(
                suggestions = listOf("parrot", "park", "part", "party"),
                onQueryChange = {},
                onSearch = {}
            )
        }
    }
}

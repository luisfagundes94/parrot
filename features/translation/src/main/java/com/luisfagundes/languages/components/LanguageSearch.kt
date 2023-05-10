package com.luisfagundes.languages.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import com.luisfagundes.languages.LanguageListEvent

@Composable
fun LanguageSearch(
    modifier: Modifier = Modifier,
    searchText: String,
    onEvent: (LanguageListEvent) -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = searchText,
        onValueChange = { newText ->
            onEvent(LanguageListEvent.OnSearchTextChanged(newText))
        },
        placeholder = {
            Text(
                text = "Search",
                fontStyle = FontStyle.Italic
            )
        },
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = null)
        },
        colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = Color.Transparent,
        )
    )
}
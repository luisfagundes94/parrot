@file:Suppress("KotlinConstantConditions")

package com.luisfagundes.saved

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.luisfagundes.domain.models.Word
import com.luisfagundes.framework.composeComponents.LoadingView
import com.luisfagundes.framework.composeComponents.WarningView
import com.luisfagundes.framework.extension.showToast
import com.luisfagundes.theme.spacing

@Composable
fun SavedScreen(
    uiState: SavedUiState,
    onEvent: (SavedUIEvent) -> Unit,
) {
    LaunchedEffect(key1 = uiState.isDeletionSuccessful) {
        onEvent(SavedUIEvent.LoadSavedWords)
    }

    showToast(
        shouldShow = uiState.shouldShowToast,
        message = stringResource(R.string.word_deleted_with_success),
    )

    when {
        uiState.isLoading -> LoadingView(
            modifier = Modifier.fillMaxSize(),
        )

        uiState.savedWords.isEmpty() -> WarningView(
            modifier = Modifier.fillMaxSize(),
            title = stringResource(R.string.empty_words_title),
            bodyMessage = stringResource(R.string.empty_words_body_message),
            animationId = com.luisfagundes.theme.R.raw.warning,
        )

        uiState.savedWords.isNotEmpty() -> SavedWords(
            words = uiState.savedWords,
            onDeleteSavedWord = { word ->
                onEvent(SavedUIEvent.DeleteSavedWord(word))
            },
        )
    }
}

@Composable
fun SavedWords(
    words: List<Word>,
    onDeleteSavedWord: (Word) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(MaterialTheme.spacing.default),
    ) {
        items(words) { word ->
            SavedWord(
                word = word,
                onDeleteButtonClicked = onDeleteSavedWord,
            )
            Spacer(
                Modifier.height(MaterialTheme.spacing.default),
            )
        }
    }
}

@Composable
fun SavedWord(
    word: Word,
    onDeleteButtonClicked: (Word) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            Text(
                text = word.text,
                fontWeight = FontWeight.Bold,
            )
            Spacer(
                Modifier.height(MaterialTheme.spacing.extraSmall),
            )
            Text(
                text = word.translations.first().text,
            )
        }
        Spacer(Modifier.weight(1f))
        IconButton(
            onClick = { onDeleteButtonClicked(word) },
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete_word),
            )
        }
    }
}

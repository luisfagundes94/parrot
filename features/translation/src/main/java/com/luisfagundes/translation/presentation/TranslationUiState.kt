package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.models.Word
import com.luisfagundes.framework.base.SingleEvent

data class TranslationUiState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val hasError: Boolean = false,
    val wordList: List<Word> = emptyList(),
    val wordSavedEvent: SingleEvent<Boolean>? = null,
    val languagePair: Pair<Language, Language>? = null,
) {
    val shouldShowSavedWordToast: Boolean
        get() = wordSavedEvent?.getContentIfNotHandled() == true && !isLoading && !hasError

    val areExamplesEmpty: Boolean
        get() = wordList.all { word ->
            word.translations.all { translation ->
                translation.examples.isEmpty()
            }
        }
}

fun TranslationUiState.toSuccessState(words: List<Word>) = this.copy(
    wordList = words,
    isLoading = false,
    hasError = false,
    isEmpty = false,
)

fun TranslationUiState.toLoadingState(): TranslationUiState = this.copy(
    isLoading = true,
    isEmpty = false,
    hasError = false,
    wordList = emptyList(),
)

fun TranslationUiState.toEmptyState(): TranslationUiState = this.copy(
    isEmpty = true,
    isLoading = false,
    hasError = false,
    wordList = emptyList(),
)

fun TranslationUiState.toErrorState(): TranslationUiState = this.copy(
    hasError = true,
    isLoading = false,
    isEmpty = false,
    wordList = emptyList(),
)

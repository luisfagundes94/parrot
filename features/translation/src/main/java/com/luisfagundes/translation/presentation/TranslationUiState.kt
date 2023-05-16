package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.models.Word

data class TranslationUiState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val hasError: Boolean = false,
    val wordList: List<Word> = emptyList(),
    val wordSavedWithSuccess: Boolean = false,
    val languagePair: Pair<Language, Language>? = null,
)

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

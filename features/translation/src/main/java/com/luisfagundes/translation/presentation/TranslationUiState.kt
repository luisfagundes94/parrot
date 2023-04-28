package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Word

data class TranslationUiState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val word: Word? = null,
    val hasError: Boolean = false,
)

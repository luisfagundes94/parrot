package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.models.Word

data class TranslationUiState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val wordList: List<Word> = emptyList(),
    val hasError: Boolean = false,
    val sourceLang: Language,
    val targetLang: Language
)

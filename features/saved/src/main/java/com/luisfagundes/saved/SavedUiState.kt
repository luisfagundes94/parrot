package com.luisfagundes.saved

import com.luisfagundes.domain.models.Word

data class SavedUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val deletedWithSuccess: Boolean = false,
    val savedWords: List<Word> = emptyList()
)

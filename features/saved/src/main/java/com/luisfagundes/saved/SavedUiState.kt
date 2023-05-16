package com.luisfagundes.saved

import com.luisfagundes.domain.models.Word

data class SavedUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val searchText: String = "",
    val isDeletionSuccessful: Boolean = false,
    val savedWords: List<Word> = emptyList(),
) {
    val shouldShowToast: Boolean
        get() = isDeletionSuccessful && !isLoading && !hasError
}

package com.luisfagundes.saved

import com.luisfagundes.domain.models.Word
import com.luisfagundes.framework.base.SingleEvent

data class SavedUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val searchText: String = "",
    val wordDeletionEvent: SingleEvent<Boolean>? = null,
    val savedWords: List<Word> = emptyList(),
) {
    val shouldShowWordDeletionToast: Boolean
        get() = wordDeletionEvent?.getContentIfNotHandled() == true && !isLoading && !hasError
}

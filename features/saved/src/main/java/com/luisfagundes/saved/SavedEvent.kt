package com.luisfagundes.saved

import com.luisfagundes.domain.models.Word

sealed class SavedEvent {
    object LoadSavedWords : SavedEvent()
    data class DeleteSavedWord(val word: Word) : SavedEvent()
    data class OnSearchTextChanged(val text: String) : SavedEvent()
}

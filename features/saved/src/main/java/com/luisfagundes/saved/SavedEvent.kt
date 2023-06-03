package com.luisfagundes.saved

import com.luisfagundes.domain.models.Word

sealed class SavedEvent {
    object OnLoadSavedWords : SavedEvent()
    data class OnDeleteSavedWord(val word: Word) : SavedEvent()
    data class OnSearchTextChanged(val text: String) : SavedEvent()
}

package com.luisfagundes.saved

import com.luisfagundes.domain.models.Word

sealed class SavedUIEvent {
    object LoadSavedWords : SavedUIEvent()
    data class DeleteSavedWord(val word: Word) : SavedUIEvent()
}

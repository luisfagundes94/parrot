package com.luisfagundes.languages

import com.luisfagundes.domain.models.Language

data class LanguageListUiState(
    val isSourceLanguage: Boolean = true,
    val languages: List<Language> = emptyList(),
    val searchText: String = "",
)

sealed class LanguageListEvent {
    data class OnSearchTextChanged(val text: String) : LanguageListEvent()
    data class OnLanguageClicked(
        val id: String,
        val isSourceLanguage: Boolean,
    ) : LanguageListEvent()

    object GetLanguageList : LanguageListEvent()
    object OnBackPressed : LanguageListEvent()
}

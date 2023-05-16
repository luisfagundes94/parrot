package com.luisfagundes.languages

import com.luisfagundes.domain.models.Language

data class LanguageListUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val isSourceLanguage: Boolean = true,
    val languages: List<Language> = emptyList(),
    val searchText: String = "",
)

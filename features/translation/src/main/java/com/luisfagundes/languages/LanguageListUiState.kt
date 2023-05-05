package com.luisfagundes.languages

import com.luisfagundes.domain.models.Country

data class LanguageListUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val countries: List<Country> = emptyList(),
)

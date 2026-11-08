package com.luisfagundes.feature.search.impl.presentation.state

import com.luisfagundes.core.common.presentation.arch.state.UiState
import com.luisfagundes.core.dictionary.domain.model.WordEntry

internal sealed interface SearchState : UiState {
    data class Error(val message: String) : SearchState
    data class Content(
        val suggestions: List<String> = emptyList(),
        val recentlyLookedUpWords: List<WordEntry> = emptyList()
    ) : SearchState
}

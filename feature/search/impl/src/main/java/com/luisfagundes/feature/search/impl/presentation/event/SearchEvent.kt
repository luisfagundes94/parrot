package com.luisfagundes.feature.search.impl.presentation.event

import com.luisfagundes.core.common.presentation.arch.event.UiEvent

internal sealed interface SearchEvent : UiEvent {
    data object OnGetRecentlyLookedUpWords : SearchEvent
    data class OnQueryChange(val query: String) : SearchEvent
    data class OnSearchClick(val query: String) : SearchEvent
}
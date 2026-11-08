package com.luisfagundes.feature.word.impl.presentation.event

import com.luisfagundes.core.common.presentation.arch.event.UiEvent

internal sealed interface WordDetailEvent : UiEvent {
    data class OnLoad(val word: String) : WordDetailEvent
    data object OnBookmarkClick : WordDetailEvent
    data object OnPlayAudioClick : WordDetailEvent
    data object OnSetReminderClick : WordDetailEvent
    data object OnBackClick : WordDetailEvent
}

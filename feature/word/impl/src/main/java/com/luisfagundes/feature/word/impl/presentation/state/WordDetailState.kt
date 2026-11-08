package com.luisfagundes.feature.word.impl.presentation.state

import com.luisfagundes.core.common.presentation.arch.state.UiState

internal sealed interface WordDetailState : UiState {
    data object Loading : WordDetailState

    data class Error(val message: String) : WordDetailState

    data class Content(
        val word: String,
        val phonetic: String?,
        val meanings: List<MeaningUi>,
        val isBookmarked: Boolean = false,
        val isPlayingAudio: Boolean = false,
    ) : WordDetailState
}

internal data class MeaningUi(
    val partOfSpeech: String,
    val definition: String,
    val examples: List<String>,
)

package com.luisfagundes.feature.word.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.core.common.tools.ResourceProvider
import com.luisfagundes.core.dictionary.domain.model.Meaning
import com.luisfagundes.core.dictionary.domain.model.WordEntry
import com.luisfagundes.core.dictionary.domain.model.WordNotFoundException
import com.luisfagundes.core.dictionary.domain.repository.DictionaryRepository
import com.luisfagundes.feature.word.impl.R
import com.luisfagundes.feature.word.impl.domain.speech.WordSpeaker
import com.luisfagundes.feature.word.impl.presentation.effect.WordDetailEffect
import com.luisfagundes.feature.word.impl.presentation.event.WordDetailEvent
import com.luisfagundes.feature.word.impl.presentation.state.MeaningUi
import com.luisfagundes.feature.word.impl.presentation.state.WordDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@HiltViewModel
internal class WordDetailViewModel @Inject constructor(
    private val repository: DictionaryRepository,
    private val wordSpeaker: WordSpeaker,
    private val resourceProvider: ResourceProvider
) : ViewModel<WordDetailState, WordDetailEvent, WordDetailEffect>(
    initialState = WordDetailState.Loading
) {
    private var requestedWord: String? = null
    private var loadJob: Job? = null
    private var playbackJob: Job? = null

    override fun dispatchEvent(event: WordDetailEvent) {
        when (event) {
            is WordDetailEvent.OnLoad -> load(event.word)
            is WordDetailEvent.OnBookmarkClick -> toggleBookmark()
            is WordDetailEvent.OnPlayAudioClick -> togglePlayback()
            is WordDetailEvent.OnSetReminderClick,
            is WordDetailEvent.OnBackClick -> sendEffect { WordDetailEffect.NavigateBack }
        }
    }

    private fun load(word: String) {
        // The screen re-sends OnLoad after configuration changes; keep what we already have.
        if (requestedWord == word && getCurrentState() !is WordDetailState.Error) return
        requestedWord = word

        playbackJob?.cancel()
        loadJob?.cancel()
        setState { WordDetailState.Loading }
        loadJob = viewModelScope.launch {
            repository.search(word)
                .onSuccess { entries ->
                    if (entries.isEmpty()) {
                        setState { WordDetailState.Error(notFoundMessage(word)) }
                    } else {
                        setState { entries.toContent() }
                    }
                }
                .onFailure { error ->
                    val message = if (error is WordNotFoundException) {
                        notFoundMessage(word)
                    } else {
                        resourceProvider.getString(R.string.something_went_wrong)
                    }
                    setState { WordDetailState.Error(message) }
                }
        }
    }

    override fun onCleared() {
        wordSpeaker.release()
        super.onCleared()
    }

    private fun notFoundMessage(word: String) =
        resourceProvider.getString(R.string.no_definition_found, word)

    // Not persisted yet: :feature:saved has no data layer, so the flag only lives in state.
    private fun toggleBookmark() {
        setStateOf<WordDetailState.Content> { it.copy(isBookmarked = !it.isBookmarked) }
    }

    private fun togglePlayback() {
        val content = getCurrentState() as? WordDetailState.Content ?: return
        if (playbackJob?.isActive == true) {
            playbackJob?.cancel()
            return
        }
        playbackJob = viewModelScope.launch {
            setStateOf<WordDetailState.Content> { it.copy(isPlayingAudio = true) }
            try {
                // A failed playback only resets the speaker; the word stays readable.
                wordSpeaker.speak(content.word)
            } finally {
                setStateOf<WordDetailState.Content> { it.copy(isPlayingAudio = false) }
            }
        }
    }

    private fun List<WordEntry>.toContent() = WordDetailState.Content(
        word = first().word,
        phonetic = firstNotNullOfOrNull { it.phonetic },
        meanings = flatMap { it.meanings }.mapNotNull { it.toUi() },
    )

    private fun Meaning.toUi(): MeaningUi? {
        val definition = definitions.firstOrNull { it.text.isNotBlank() } ?: return null
        return MeaningUi(
            partOfSpeech = partOfSpeech,
            definition = definition.text,
            examples = definitions
                .mapNotNull { it.example?.takeIf(String::isNotBlank) }
                .distinct(),
        )
    }
}

package com.luisfagundes.feature.search.impl.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.luisfagundes.core.common.presentation.arch.viewmodel.ViewModel
import com.luisfagundes.core.common.tools.ResourceProvider
import com.luisfagundes.feature.search.impl.R
import com.luisfagundes.core.dictionary.domain.repository.DictionaryRepository
import com.luisfagundes.feature.search.impl.domain.repository.WordSuggestionRepository
import com.luisfagundes.feature.search.impl.presentation.effect.SearchEffect
import com.luisfagundes.feature.search.impl.presentation.event.SearchEvent
import com.luisfagundes.feature.search.impl.presentation.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val repository: DictionaryRepository,
    private val suggestionRepository: WordSuggestionRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel<SearchState, SearchEvent, SearchEffect>(
    initialState = SearchState.Content()
) {
    private var suggestionsJob: Job? = null

    override fun dispatchEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnGetRecentlyLookedUpWords -> observeRecentlyLookedUpWords()
            is SearchEvent.OnQueryChange -> updateSuggestions(event.query)
            is SearchEvent.OnSearchClick -> searchWord(event.query)
        }
    }

    private fun observeRecentlyLookedUpWords() = viewModelScope.launch {
        repository.getRecentlyLookedUpWords()
            .catch {
                val errorMessage = resourceProvider.getString(R.string.something_went_wrong)
                setState { SearchState.Error(errorMessage) }
            }
            .collect { words ->
                if (words.isNullOrEmpty()) {
                    setStateOf<SearchState.Content> { it.copy(recentlyLookedUpWords = emptyList()) }
                } else {
                    setStateOf<SearchState.Content> { it.copy(recentlyLookedUpWords = words) }
                }
            }
    }

    private fun updateSuggestions(query: String) {
        suggestionsJob?.cancel()
        suggestionsJob = viewModelScope.launch {
            delay(SUGGESTION_DEBOUNCE_MS)
            val suggestions = try {
                suggestionRepository.suggest(query)
            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                // Autocomplete is best-effort: failing to load it must not break the screen.
                emptyList()
            }
            setStateOf<SearchState.Content> { it.copy(suggestions = suggestions) }
        }
    }

    private fun searchWord(query: String) = viewModelScope.launch {
        repository.search(query)
            .onFailure {
                val errorMessage = resourceProvider.getString(R.string.something_went_wrong)
                setState { SearchState.Error(errorMessage) }
            }
            .onSuccess { words ->
                if (words.isEmpty()) {
                    setStateOf<SearchState.Content> { it.copy(searchResults = emptyList()) }
                } else {
                    setStateOf<SearchState.Content> { it.copy(searchResults = words) }
                }
            }
    }

    private companion object {
        const val SUGGESTION_DEBOUNCE_MS = 100L
    }
}

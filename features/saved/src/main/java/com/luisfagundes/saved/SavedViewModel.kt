package com.luisfagundes.saved

import androidx.lifecycle.viewModelScope
import com.luisfagundes.commonsUtil.Time.FIVE_SECONDS
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.CancelNotification
import com.luisfagundes.domain.usecases.DeleteWord
import com.luisfagundes.domain.usecases.GetAllSavedWords
import com.luisfagundes.framework.base.BaseViewModel
import com.luisfagundes.framework.base.DefaultDispatcher
import com.luisfagundes.framework.base.SingleEvent
import com.luisfagundes.framework.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getAllSavedWords: GetAllSavedWords,
    private val deleteWord: DeleteWord,
    private val cancelNotification: CancelNotification,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SavedUiState())
    val uiState = _uiState
        .map { filterSavedWords(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(FIVE_SECONDS),
            _uiState.value,
        )

    fun onEvent(event: SavedEvent) {
        when (event) {
            is SavedEvent.LoadSavedWords -> fetchAllSavedWords()
            is SavedEvent.DeleteSavedWord -> deleteSavedWord(event.word)
            is SavedEvent.OnSearchTextChanged -> updateSearchText(event.text)
        }
    }

    private fun fetchAllSavedWords() = safeLaunch {
        startLoading()
        val result = withContext(dispatcher) {
            getAllSavedWords()
        }
        _uiState.update {
            it.copy(
                isLoading = false,
                hasError = false,
                savedWords = result,
            )
        }
    }

    private fun deleteSavedWord(word: Word) = safeLaunch {
        val result = withContext(dispatcher) {
            deleteWord(word)
        }
        handleWordDeletionResult(
            result,
            word.id,
        )
    }

    private fun updateSearchText(text: String) {
        _uiState.update {
            it.copy(
                searchText = text,
            )
        }
    }

    private fun handleWordDeletionResult(
        result: DataState<Unit>,
        wordId: Int,
    ) {
        if (result is DataState.Success) {
            updateWordDeletionEvent()
            cancelNotification(wordId)
        }
    }

    private fun updateWordDeletionEvent() {
        _uiState.update {
            it.copy(wordDeletionEvent = SingleEvent(true))
        }
    }

    private fun filterSavedWords(
        uiState: SavedUiState,
    ) =
        if (uiState.searchText.isBlank()) {
            uiState
        } else {
            uiState.copy(
                savedWords = uiState.savedWords.filter { word ->
                    word.doesMatchSearch(
                        uiState.searchText,
                    )
                },
            )
        }
}

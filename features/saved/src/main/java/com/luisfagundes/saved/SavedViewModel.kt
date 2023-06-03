package com.luisfagundes.saved

import androidx.lifecycle.viewModelScope
import com.luisfagundes.commonsUtil.Time.FIVE_SECONDS
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.CancelNotification
import com.luisfagundes.domain.usecases.DeleteWord
import com.luisfagundes.domain.usecases.GetAllSavedWords
import com.luisfagundes.framework.base.BaseViewState
import com.luisfagundes.framework.base.DefaultDispatcher
import com.luisfagundes.framework.base.MviViewModel
import com.luisfagundes.framework.base.SingleEvent
import com.luisfagundes.framework.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getAllSavedWords: GetAllSavedWords,
    private val deleteWord: DeleteWord,
    private val cancelNotification: CancelNotification,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : MviViewModel<BaseViewState<SavedUiState>, SavedEvent>() {

    val state = uiState
        .map { filterSavedWords(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(FIVE_SECONDS),
            uiState.value,
        )

    override fun onEvent(event: SavedEvent) {
        when (event) {
            is SavedEvent.OnLoadSavedWords -> fetchAllSavedWords()
            is SavedEvent.OnDeleteSavedWord -> deleteSavedWord(event.word)
            is SavedEvent.OnSearchTextChanged -> updateSearchText(event.text)
        }
    }

    private fun fetchAllSavedWords() = safeLaunch {
        startLoading()
        val savedWords = withContext(dispatcher) {
            getAllSavedWords()
        }
        setState(
            BaseViewState.Data(
                SavedUiState(savedWords = savedWords),
            ),
        )
    }

    private fun deleteSavedWord(word: Word) = safeLaunch {
        val result = withContext(dispatcher) {
            deleteWord(word)
        }
        if (result is DataState.Success) {
            updateWordDeletionEvent()
            cancelNotification(word.id)
        }
    }

    private fun updateSearchText(text: String) {
        setState(
            BaseViewState.Data(
                SavedUiState(searchText = text),
            ),
        )
    }

    private fun updateWordDeletionEvent() {
        setState(
            BaseViewState.Data(
                SavedUiState(
                    wordDeletionEvent = SingleEvent(true),
                ),
            ),
        )
    }

    private fun filterSavedWords(state: BaseViewState<*>) =
        when (state) {
            is BaseViewState.Data -> {
                (state.value as? SavedUiState)?.let { uiState ->
                    val filteredWords = uiState.savedWords.filter {
                        it.doesMatchSearch(uiState.searchText)
                    }
                    BaseViewState.Data(
                        uiState.copy(savedWords = filteredWords),
                    )
                }
            }

            else -> state
        }
}

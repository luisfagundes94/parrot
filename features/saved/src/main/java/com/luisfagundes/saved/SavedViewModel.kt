package com.luisfagundes.saved

import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.DeleteWord
import com.luisfagundes.domain.usecases.GetAllSavedWords
import com.luisfagundes.framework.base.BaseViewModel
import com.luisfagundes.framework.base.DefaultDispatcher
import com.luisfagundes.framework.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
  private val getAllSavedWords: GetAllSavedWords,
  private val deleteWord: DeleteWord,
  @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel() {

  private val _uiState = MutableStateFlow(SavedUiState())
  val uiState = _uiState.asStateFlow()

  fun onEvent(event: SavedUIEvent) {
    when (event) {
      is SavedUIEvent.LoadSavedWords -> fetchAllSavedWords()
      is SavedUIEvent.DeleteSavedWord -> deleteSavedWord(event.word)
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
    if (result is DataState.Success) {
      updateWordDeletedWithSuccess(true)
    } else {
      updateWordDeletedWithSuccess(false)
    }
  }

  private fun updateWordDeletedWithSuccess(isSuccess: Boolean) {
    _uiState.update {
      it.copy(isDeletionSuccessful = isSuccess)
    }
  }
}

package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.GetLanguagePair
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.domain.usecases.SaveWord
import com.luisfagundes.framework.base.BaseViewModel
import com.luisfagundes.framework.base.DefaultDispatcher
import com.luisfagundes.framework.network.DataState
import com.luisfagundes.framework.utils.doNothing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val getWordTranslations: GetWordTranslations,
    private val getLanguagePair: GetLanguagePair,
    private val saveWord: SaveWord,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(TranslationUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: TranslationUIEvent) = safeLaunch {
        when (event) {
            is TranslationUIEvent.Translate -> translateWord(event.text)
            is TranslationUIEvent.InvertLanguagePair -> invertLanguages(event.languagePair)
            is TranslationUIEvent.UpdateLanguagePair -> updateLanguagePair()
            is TranslationUIEvent.OnLanguageClicked -> doNothing()
            is TranslationUIEvent.SaveWord -> saveWordToLocalDb(event.word)
        }
    }

    private fun translateWord(text: String) = safeLaunch {
        if (text.length < 2) return@safeLaunch

        val languagePair = _uiState.value.languagePair ?: return@safeLaunch

        val firstCode = languagePair.first.code
        val secondCode = languagePair.second.code

        val params = GetWordTranslations.Params(
            text = text,
            sourceLanguage = firstCode,
            destLanguage = secondCode
        )

        startLoading()
        val result = getWordTranslations(params)
        handleResult(result) { words ->
            _uiState.update { it.toSuccessState(words) }
        }
    }

    private fun invertLanguages(languagePair: Pair<Language, Language>?) {
        if (languagePair == null) return

        _uiState.update {
            it.copy(
                languagePair = Pair(languagePair.second, languagePair.first)
            )
        }
    }

    private fun saveWordToLocalDb(word: Word) = safeLaunch {
        val result = withContext(dispatcher) { saveWord(word) }
        _uiState.update {
            when (result) {
                is DataState.Success -> it.copy(wordSavedWithSuccess = true)
                is DataState.Error -> it.copy(wordSavedWithSuccess = false)
                else -> it
            }
        }
        delay(500L)
        _uiState.update { it.copy(wordSavedWithSuccess = false) }
    }

    private fun updateLanguagePair() = safeLaunch {
        _uiState.update {
            it.copy(languagePair = getLanguagePair())
        }
    }

    override fun startLoading() {
        _uiState.update { it.toLoadingState() }
    }

    override fun handleEmpty() {
        _uiState.update { it.toEmptyState() }
    }

    override fun handleError(exception: Throwable) {
        println(exception.stackTraceToString())
        _uiState.update { it.toErrorState() }
    }
}
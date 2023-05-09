package com.luisfagundes.translation.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.luisfagundes.commons_util.RouteParams.IS_SOURCE_LANGUAGE
import com.luisfagundes.commons_util.RouteParams.LANGUAGE_ID
import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.GetLanguagePair
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.framework.base.BaseViewModel
import com.luisfagundes.framework.extension.empty
import com.luisfagundes.framework.utils.doNothing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val getWordTranslations: GetWordTranslations,
    private val getLanguagePair: GetLanguagePair
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(TranslationUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: TranslationUIEvent) = safeLaunch {
        when (event) {
            is TranslationUIEvent.Translate -> translateWord(event.text)
            is TranslationUIEvent.InvertLanguagePair -> invertLanguages(event.languagePair)
            is TranslationUIEvent.UpdateLanguagePair -> updateLanguagePair()
            is TranslationUIEvent.OnLanguageClicked -> doNothing()
        }
    }

    private fun updateLanguagePair() = safeLaunch {
        startLoading()

        val languagePair = getLanguagePair()
        _uiState.update {
            it.copy(
                languagePair = languagePair,
                isLoading = false,
                isEmpty = false,
                hasError = false
            )
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
        handleResult(result)
    }

    private fun invertLanguages(languagePair: Pair<Language, Language>?) {
        if (languagePair == null) return

        _uiState.update {
            it.copy(
                languagePair = Pair(languagePair.second, languagePair.first)
            )
        }
    }

    override fun startLoading() {
        _uiState.update {
            it.copy(
                isLoading = true,
                isEmpty = false,
                hasError = false,
                wordList = emptyList()
            )
        }
    }

    override fun handleEmpty() {
        _uiState.update {
            it.copy(
                isEmpty = true,
                isLoading = false,
                hasError = false,
                wordList = emptyList()
            )
        }
    }

    override fun handleError(exception: Throwable) {
        println(exception.stackTraceToString())
        _uiState.update {
            it.copy(
                hasError = true,
                isLoading = false,
                isEmpty = false,
                wordList = emptyList()
            )
        }
    }

    override fun handleSuccess(result: Any?) {
        _uiState.update {
            it.copy(
                wordList = result as? List<Word> ?: emptyList(),
                isLoading = false,
                hasError = false,
                isEmpty = false
            )
        }
    }
}
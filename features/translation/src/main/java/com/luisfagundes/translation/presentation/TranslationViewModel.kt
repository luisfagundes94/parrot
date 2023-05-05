package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.GetLanguagePair
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.framework.base.BaseViewModel
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

    private val _uiState = MutableStateFlow(
        TranslationUiState(
            languagePair = getDefaultLanguagePair()
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: TranslationEvent) = safeLaunch {
        when (event) {
            is TranslationEvent.Translate -> translateWord(event.text)
            is TranslationEvent.InvertCountries -> invertLanguages(event.countries)
            is TranslationEvent.UpdateCountryPair -> updateLanguagePair()
        }
    }

    private fun getDefaultLanguagePair() = Pair(
        Language(
            name = "English",
            nativeName = "English",
            code = "en"
        ),
        Language(
            name = "Portuguese",
            nativeName = "Português",
            code = "pt"
        ),
    )

    private fun translateWord(text: String) = safeLaunch {
        if (text.length < 2) return@safeLaunch

        val firstCode = _uiState.value.languagePair.first.code
        val secondCode = _uiState.value.languagePair.second.code

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

    private suspend fun updateLanguagePair() {
        val languagePair = getLanguagePair()
        _uiState.update {
            it.copy(
                languagePair = languagePair
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
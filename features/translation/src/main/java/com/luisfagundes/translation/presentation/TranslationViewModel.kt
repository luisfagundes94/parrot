package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.GetLanguageName
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.framework.base.BaseViewModel
import com.luisfagundes.translation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val getWordTranslations: GetWordTranslations,
    private val getLanguageName: GetLanguageName
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(
        TranslationUiState(
            sourceLang = Language(R.drawable.us, getLangName("en")),
            targetLang = Language(R.drawable.br, getLangName("br"))
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: TranslationEvent) {
        when (event) {
            is TranslationEvent.Translate -> translateWord(event.text)
            is TranslationEvent.GetLanguageName -> getLangName(event.countryCode)
        }
    }

    private fun translateWord(text: String) {
        if (text.length < 2) return

        val params = hashMapOf(
            "query" to text,
            "src" to "en",
            "dst" to "pt"
        )
        safeLaunch {
            startLoading()
            val result = getWordTranslations(params)
            handleResult(result)
        }
    }

    private fun getLangName(countryCode: String): String {
        return getLanguageName(countryCode)
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
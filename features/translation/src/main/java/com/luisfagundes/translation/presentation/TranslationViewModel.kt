package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.GetLanguageName
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
    private val getLanguageName: GetLanguageName
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(TranslationUiState())
    val uiState = _uiState.asStateFlow()

    fun translateWord(text: String) {
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

    fun getLanguageDisplayName(countryCode: String): String {
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
                isLoading = false,
                isEmpty = true,
                hasError = false,
                wordList = emptyList()
            )
        }
    }

    override fun handleError(exception: Throwable) {
        println(exception.stackTraceToString())
        _uiState.update {
            it.copy(
                isLoading = false,
                isEmpty = false,
                hasError = true,
                wordList = emptyList()
            )
        }
    }

    override fun handleSuccess(result: Any?) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isEmpty = false,
                hasError = false,
                wordList = result as? List<Word> ?: emptyList()
            )
        }
    }
}
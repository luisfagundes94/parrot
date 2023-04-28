package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.framework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val getWordTranslations: GetWordTranslations
): BaseViewModel() {

    private val _uiState = MutableStateFlow(
        TranslationUiState(
            isLoading = true,
            isEmpty = false,
            hasError = false,
            word = null
        )
    )
    val uiState = _uiState.asStateFlow()

    fun translateWord(text: String) {
        if (text.length < 2) return

        val params = hashMapOf(
            "query" to text,
            "src" to "en",
            "dst" to "pt"
        )
        safeLaunch {
            val result = getWordTranslations(params)
            handleResult(result)
        }
    }

    override fun handleEmpty() {
        _uiState.update {
            it.copy(
                isLoading = false,
                isEmpty = true,
                hasError = false,
                word = null
            )
        }
    }

    override fun handleError(exception: Throwable) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isEmpty = false,
                hasError = true,
                word = null
            )
        }
    }

    override fun handleSuccess(result: Any?) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isEmpty = false,
                hasError = false,
                word = result as? Word
            )
        }
    }
}
package com.luisfagundes.settings.applanguages

import com.luisfagundes.framework.base.BaseViewModel
import com.luisfagundes.provider.LanguageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AppLanguageViewModel @Inject constructor(
    private val languageProvider: LanguageProvider,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(AppLanguageUiState())
    val uiState = _uiState.asStateFlow()

    init {
        safeLaunch {
            updateUiState(
                languageCode = languageProvider.getLanguageCode(),
            )
        }
    }

    fun onEvent(event: AppLanguageEvent) {
        when (event) {
            is AppLanguageEvent.OnSaveAppLanguage -> safeLaunch {
                languageProvider.saveLanguageCode(
                    languageCode = event.language.code,
                )
                updateUiState(
                    languageCode = event.language.code,
                )
            }
        }
    }

    private fun updateUiState(languageCode: String) {
        _uiState.update { state ->
            val updatedLanguages = state.languages.map {
                it.copy(isSelected = it.code == languageCode)
            }
            state.copy(languages = updatedLanguages)
        }
    }
}

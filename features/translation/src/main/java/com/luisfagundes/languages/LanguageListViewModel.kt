package com.luisfagundes.languages

import androidx.lifecycle.SavedStateHandle
import com.luisfagundes.commons_util.RouteParams.IS_SOURCE_LANGUAGE
import com.luisfagundes.domain.usecases.ListLanguages
import com.luisfagundes.framework.base.BaseViewModel
import com.luisfagundes.framework.utils.doNothing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LanguageListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getLanguageList: ListLanguages
): BaseViewModel() {

    private val isSourceLanguage = savedStateHandle.getStateFlow(
        IS_SOURCE_LANGUAGE,
        true
    )

    private val _uiState = MutableStateFlow(
        LanguageListUiState(
            isSourceLanguage = isSourceLanguage.value,
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        listLanguages()
    }

    fun onEvent(event: LanguageListEvent) = safeLaunch {
        when (event) {
            is LanguageListEvent.GetLanguageList -> listLanguages()
            is LanguageListEvent.OnBackPressed -> doNothing()
            is LanguageListEvent.OnLanguageClicked -> doNothing()
        }
    }

    private fun listLanguages() = safeLaunch {
        startLoading()
        val languageList = getLanguageList()
        _uiState.update {
            it.copy(
                isLoading = false,
                hasError = false,
                languages = languageList
            )
        }
    }
}
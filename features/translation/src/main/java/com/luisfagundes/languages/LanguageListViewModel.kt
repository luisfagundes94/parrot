package com.luisfagundes.languages

import androidx.lifecycle.SavedStateHandle
import com.luisfagundes.commons_util.RouteParams.IS_SOURCE_LANGUAGE
import com.luisfagundes.domain.usecases.ListLanguages
import com.luisfagundes.domain.usecases.UpdateLanguage
import com.luisfagundes.framework.base.BaseViewModel
import com.luisfagundes.framework.utils.doNothing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LanguageListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLanguageList: ListLanguages,
    private val updateLanguage: UpdateLanguage
): BaseViewModel() {

    private val isSourceLanguage = checkNotNull<Boolean>(
        savedStateHandle[IS_SOURCE_LANGUAGE]
    )

    private val _uiState = MutableStateFlow(
        LanguageListUiState(
            isSourceLanguage = isSourceLanguage,
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LanguageListEvent) = safeLaunch {
        when (event) {
            is LanguageListEvent.GetLanguageList -> listLanguages()
            is LanguageListEvent.OnBackPressed -> doNothing()
            is LanguageListEvent.OnLanguageClicked -> updateLanguage(event.id)
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

    private fun updateLanguage(id: String) = safeLaunch {
        updateLanguage(
            id = id,
            isSourceLanguage = isSourceLanguage
        )
    }
}
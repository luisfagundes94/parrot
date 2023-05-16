package com.luisfagundes.languages

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.luisfagundes.commonsUtil.RouteParams.IS_SOURCE_LANGUAGE
import com.luisfagundes.commonsUtil.Time.FIVE_SECONDS
import com.luisfagundes.domain.usecases.ListLanguages
import com.luisfagundes.domain.usecases.UpdateLanguage
import com.luisfagundes.framework.base.BaseViewModel
import com.luisfagundes.framework.utils.doNothing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LanguageListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLanguageList: ListLanguages,
    private val updateLanguage: UpdateLanguage,
) : BaseViewModel() {

    private val isSourceLanguage = checkNotNull<Boolean>(
        savedStateHandle[IS_SOURCE_LANGUAGE],
    )

    private val _uiState = MutableStateFlow(
        LanguageListUiState(
            isSourceLanguage = isSourceLanguage,
        ),
    )
    val uiState = _uiState
        .map { filterLanguageList(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(FIVE_SECONDS),
            _uiState.value,
        )

    fun onEvent(event: LanguageListEvent) = safeLaunch {
        when (event) {
            is LanguageListEvent.GetLanguageList -> listLanguages()
            is LanguageListEvent.OnBackPressed -> doNothing()
            is LanguageListEvent.OnSearchTextChanged -> updateSearchText(event.text)
            is LanguageListEvent.OnLanguageClicked -> updateLanguage(event.id)
        }
    }

    private fun filterLanguageList(
        uiState: LanguageListUiState,
    ) =
        if (uiState.searchText.isBlank()) {
            uiState
        } else {
            uiState.copy(
                languages = uiState.languages.filter {
                    it.doesMatchSearch(
                        uiState.searchText,
                    )
                },
            )
        }

    private fun updateSearchText(text: String) {
        _uiState.update {
            it.copy(
                searchText = text,
            )
        }
    }

    private fun listLanguages() = safeLaunch {
        startLoading()
        val languageList = getLanguageList()
        _uiState.update {
            it.copy(
                isLoading = false,
                hasError = false,
                languages = languageList,
            )
        }
    }

    private fun updateLanguage(id: String) = safeLaunch {
        updateLanguage(
            id = id,
            isSourceLanguage = isSourceLanguage,
        )
    }
}

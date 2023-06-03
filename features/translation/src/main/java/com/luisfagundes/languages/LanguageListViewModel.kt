package com.luisfagundes.languages

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.luisfagundes.commonsUtil.RouteParams.IS_SOURCE_LANGUAGE
import com.luisfagundes.commonsUtil.Time.FIVE_SECONDS
import com.luisfagundes.domain.usecases.ListLanguages
import com.luisfagundes.domain.usecases.UpdateLanguage
import com.luisfagundes.framework.base.BaseViewState
import com.luisfagundes.framework.base.MviViewModel
import com.luisfagundes.framework.utils.doNothing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LanguageListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLanguageList: ListLanguages,
    private val updateLanguage: UpdateLanguage,
) : MviViewModel<BaseViewState<LanguageListUiState>, LanguageListEvent>() {

    private val isSourceLanguage = checkNotNull<Boolean>(
        savedStateHandle[IS_SOURCE_LANGUAGE],
    )
    val state = uiState.map {
        filterLanguageList(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(FIVE_SECONDS),
        uiState.value,
    )

    override fun onEvent(event: LanguageListEvent) = safeLaunch {
        when (event) {
            is LanguageListEvent.GetLanguageList -> listLanguages()
            is LanguageListEvent.OnBackPressed -> doNothing()
            is LanguageListEvent.OnSearchTextChanged -> updateSearchText(event.text)
            is LanguageListEvent.OnLanguageClicked -> updateLanguage(event.id)
        }
    }

    private fun filterLanguageList(state: BaseViewState<*>) =
        when (state) {
            is BaseViewState.Data -> {
                (state.value as? LanguageListUiState)?.let { uiState ->
                    val filteredLanguages = uiState.languages.filter {
                        it.name.contains(uiState.searchText, ignoreCase = true)
                    }
                    BaseViewState.Data(
                        uiState.copy(languages = filteredLanguages)
                    )
                }
            }
            else -> state
        }

    private fun updateSearchText(text: String) {
        setState(
            BaseViewState.Data(
                LanguageListUiState(searchText = text)
            )
        )
    }

    private fun listLanguages() = safeLaunch {
        startLoading()
        setState(
            BaseViewState.Data(
                LanguageListUiState(languages = getLanguageList())
            )
        )
    }

    private fun updateLanguage(id: String) = safeLaunch {
        updateLanguage(
            id = id,
            isSourceLanguage = isSourceLanguage,
        )
    }
}

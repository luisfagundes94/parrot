package com.luisfagundes.languages

import com.luisfagundes.domain.usecases.ListCountries
import com.luisfagundes.framework.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LanguageListViewModel @Inject constructor(
    private val getCountryList: ListCountries
): BaseViewModel() {

    private val _uiState = MutableStateFlow(LanguageListUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LanguageListEvent) = safeLaunch {
        when (event) {
            is LanguageListEvent.GetCountryList -> listCountries()
        }
    }

    private fun listCountries() = safeLaunch {
        startLoading()
        val countries = getCountryList()
        _uiState.update {
            it.copy(
                isLoading = false,
                hasError = false,
                countries = countries
            )
        }
    }
}
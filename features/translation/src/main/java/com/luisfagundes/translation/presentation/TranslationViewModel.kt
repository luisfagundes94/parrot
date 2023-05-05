package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Country
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.GetLanguageName
import com.luisfagundes.domain.usecases.GetCountryPair
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
    private val getLanguageName: GetLanguageName,
    private val getCountryPair: GetCountryPair
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(
        TranslationUiState(
            countryPair = getDefaultCountryPair()
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: TranslationEvent) = safeLaunch {
        when (event) {
            is TranslationEvent.Translate -> translateWord(event.text)
            is TranslationEvent.GetLanguageName -> getLangName(event.countryCode)
            is TranslationEvent.InvertCountries -> invertCountries(event.countries)
            is TranslationEvent.UpdateCountryPair -> updateSourceAndDestCountries()
        }
    }

    private fun getDefaultCountryPair() = Pair(
        Country(
            name = "United States",
            code = "US",
            flagUrl = "https://flagsapi.com/US/flat/64.png",
            languages = listOf("English")
        ),
        Country(
            name = "Brazil",
            code = "BR",
            flagUrl = "https://flagsapi.com/BR/flat/64.png",
            languages = listOf("Portuguese")
        ),
    )

    private fun translateWord(text: String) {
        if (text.length < 2) return

        val params = GetWordTranslations.Params(
            text = text,
            sourceLanguage = _uiState.value.countryPair.first.code,
            destLanguage = _uiState.value.countryPair.second.code
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

    private fun invertCountries(countries: Pair<Country, Country>?) {
        if (countries == null) return

        _uiState.update {
            it.copy(
                countryPair = Pair(countries.second, countries.first)
            )
        }
    }

    private suspend fun updateSourceAndDestCountries() {
        val sourceAndDestCountries = getCountryPair()
        _uiState.update {
            it.copy(
                countryPair = sourceAndDestCountries
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
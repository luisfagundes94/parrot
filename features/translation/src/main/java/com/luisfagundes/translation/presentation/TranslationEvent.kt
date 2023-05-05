package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Country

sealed class TranslationEvent {
    data class Translate(val text: String) : TranslationEvent()
    data class GetLanguageName(val countryCode: String) : TranslationEvent()
    data class InvertCountries(val countries: Pair<Country, Country>?) : TranslationEvent()
    data class UpdateCountryPair(val countryPair: Pair<Country, Country>) : TranslationEvent()
}

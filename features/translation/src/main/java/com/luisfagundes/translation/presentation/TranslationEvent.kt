package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Language

sealed class TranslationEvent {
    data class Translate(val text: String) : TranslationEvent()
    data class InvertCountries(val countries: Pair<Language, Language>?) : TranslationEvent()
    data class UpdateCountryPair(val languagePair: Pair<Language, Language>) : TranslationEvent()
}

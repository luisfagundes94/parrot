package com.luisfagundes.translation.presentation

sealed class TranslationEvent {
    data class Translate(val text: String) : TranslationEvent()
    data class GetLanguageName(val countryCode: String) : TranslationEvent()
}

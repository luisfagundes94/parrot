package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Language

sealed class TranslationUIEvent {
    data class Translate(val text: String) : TranslationUIEvent()
    data class InvertLanguagePair(val languagePair: Pair<Language, Language>?) : TranslationUIEvent()
    object UpdateLanguagePair : TranslationUIEvent()
    data class OnLanguageClicked(val isSourceLanguage: Boolean) : TranslationUIEvent()
}

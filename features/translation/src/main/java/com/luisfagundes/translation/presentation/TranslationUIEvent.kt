package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Language

sealed class TranslationUIEvent {
    data class Translate(val text: String) : TranslationUIEvent()
    data class InvertLanguagePair(val languagePair: Pair<Language, Language>?) : TranslationUIEvent()
    data class UpdateLanguagePair(val languagePair: Pair<Language, Language>) : TranslationUIEvent()
    data class LanguageSelectionRequested(val isSourceLanguage: Boolean) : TranslationUIEvent()
}

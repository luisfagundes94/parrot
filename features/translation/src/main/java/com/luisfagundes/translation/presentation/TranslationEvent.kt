package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.models.ScheduleData
import com.luisfagundes.domain.models.Word

sealed class TranslationEvent {
    data class Translate(val text: String) : TranslationEvent()
    data class SaveWord(val scheduleData: ScheduleData, val word: Word) : TranslationEvent()
    data class InvertLanguagePair(val languagePair: Pair<Language, Language>?) : TranslationEvent()
    object UpdateLanguagePair : TranslationEvent()
    data class OnLanguageClicked(val isSourceLanguage: Boolean) : TranslationEvent()
}

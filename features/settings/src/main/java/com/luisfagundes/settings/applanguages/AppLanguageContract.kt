package com.luisfagundes.settings.applanguages

import com.luisfagundes.domain.models.AppLanguage

sealed class AppLanguageEvent {
    data class OnSaveAppLanguage(
        val language: AppLanguage,
    ) : AppLanguageEvent()
}

data class AppLanguageUiState(
    val languages: List<AppLanguage> = supportedLanguages,
)

val supportedLanguages = listOf(
    AppLanguage("English", "en", true),
    AppLanguage("Portuguese", "pt", false),
    AppLanguage("Spanish", "es", false),
)

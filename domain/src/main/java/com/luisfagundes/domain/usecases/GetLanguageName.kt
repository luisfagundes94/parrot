package com.luisfagundes.domain.usecases

import android.icu.util.ULocale
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.Locale

@ViewModelScoped
class GetLanguageName {
    operator fun invoke(countryCode: String): String {
        val languageCode = getLanguageFromCountryCode(countryCode)
        val locale = Locale(languageCode)
        return locale.displayLanguage
    }

    private fun getLanguageFromCountryCode(countryCode: String): String {
        val localeWithCountryCode = ULocale("", countryCode)
        val likelyLanguageTag = ULocale.addLikelySubtags(localeWithCountryCode)
        return likelyLanguageTag.language
    }
}
package com.luisfagundes.parrotlingo.provider

import android.content.Context
import com.luisfagundes.framework.pref.CacheManager
import com.luisfagundes.provider.LanguageProvider
import java.util.Locale

class AppLanguageProvider(
    private val cacheManager: CacheManager,
) : LanguageProvider {

    companion object {
        private const val LANGUAGE_CODE_KEY = "language_code"
        private const val DEFAULT_LANGUAGE = "en"
    }

    override suspend fun saveLanguageCode(languageCode: String) {
        cacheManager.write(
            key = LANGUAGE_CODE_KEY,
            value = languageCode,
        )
    }

    override suspend fun getLanguageCode() =
        cacheManager.read(
            key = LANGUAGE_CODE_KEY,
            defaultValue = DEFAULT_LANGUAGE,
        )

    @Suppress("DEPRECATION")
    override fun setLocale(
        language: String,
        context: Context,
    ) {
        val locale = Locale(language)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}

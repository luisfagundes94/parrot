package com.luisfagundes.provider

import android.content.Context

interface LanguageProvider {
    suspend fun saveLanguageCode(languageCode: String)
    suspend fun getLanguageCode(): String
    fun setLocale(
        language: String,
        context: Context
    )
}

package com.luisfagundes.data.store

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LanguageDataStore(
    context: Context
) {
    private val Context.dataStore by preferencesDataStore(
        name = "savedCountries"
    )

    private val sourceCountryIdKey = stringPreferencesKey("sourceCountry")
    private val destCountryIdKey = stringPreferencesKey("destCountry")

    val sourceLanguageId: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[sourceCountryIdKey] ?: ENGLISH
        }

    val destLanguageId: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[destCountryIdKey] ?: PORTUGUESE
        }

    private companion object {
        const val ENGLISH = "65b16faf-028d-424b-beb0-15264505ab4e"
        const val PORTUGUESE = "d7b2a4e1-bcce-477b-9332-48951d30f624"
    }
}
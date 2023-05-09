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
        const val ENGLISH = "d4d5d87e-c0a1-4f35-bb81-5f2709e628aa"
        const val PORTUGUESE = "06305ea0-5ab9-4b2d-bebc-c99e93bc5d59"
    }
}
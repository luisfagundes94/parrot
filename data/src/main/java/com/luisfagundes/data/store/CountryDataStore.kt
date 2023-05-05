package com.luisfagundes.data.store

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CountryDataStore(
    private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(
        name = "savedCountries"
    )

    private val sourceCountryIdKey = stringPreferencesKey("sourceCountry")
    private val destCountryIdKey = stringPreferencesKey("destCountry")

    val sourceCountryId: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[sourceCountryIdKey] ?: DEFAULT_SOURCE_COUNTRY
        }

    val destCountryId: Flow<String> =
        context.dataStore.data.map { preferences ->
            preferences[destCountryIdKey] ?: DEFAULT_DEST_COUNTRY
        }

    private companion object {
        const val DEFAULT_SOURCE_COUNTRY = "9f1efd5e-792c-4e05-a355-1562c5fe5daf"
        const val DEFAULT_DEST_COUNTRY = "a4405813-3cbe-46a4-850c-ed0ab6655bb8"
    }
}
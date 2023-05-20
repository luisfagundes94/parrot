package com.luisfagundes.framework.pref

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private const val DEFAULT_SHARED_PREF_NAME = "default_shared_pref"

class CacheManager(
    context: Context,
    fileName: String? = null,
) {
    private val Context.dataStore by preferencesDataStore(
        name = fileName ?: DEFAULT_SHARED_PREF_NAME,
    )
    private val dataStore = context.dataStore

    @Suppress("UNCHECKED_CAST")
    suspend fun <T> read(key: String, defaultValue: T): T {
        val preferences = dataStore.data.first()
        return when (defaultValue) {
            is String -> preferences[stringPreferencesKey(key)]?.let { it as T } ?: defaultValue
            is Int -> preferences[intPreferencesKey(key)]?.let { it as T } ?: defaultValue
            is Boolean -> preferences[booleanPreferencesKey(key)]?.let { it as T } ?: defaultValue
            is Long -> preferences[longPreferencesKey(key)]?.let { it as T } ?: defaultValue
            is Double -> preferences[doublePreferencesKey(key)]?.let { it as T } ?: defaultValue
            is Float -> preferences[floatPreferencesKey(key)]?.let { it as T } ?: defaultValue
            else -> defaultValue
        }
    }

    suspend fun <T> write(key: String, value: T) {
        when (value) {
            is String -> dataStore.edit { it[stringPreferencesKey(key)] = value }
            is Int -> dataStore.edit { it[intPreferencesKey(key)] = value }
            is Boolean -> dataStore.edit { it[booleanPreferencesKey(key)] = value }
            is Long -> dataStore.edit { it[longPreferencesKey(key)] = value }
            is Double -> dataStore.edit { it[doublePreferencesKey(key)] = value }
            is Float -> dataStore.edit { it[floatPreferencesKey(key)] = value }
            else -> Unit
        }
    }

    suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

package com.luisfagundes.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LanguageDataStore(
  private val context: Context,
) {
  private val Context.dataStore by preferencesDataStore(
    name = "savedLanguagePair",
  )

  private val sourceLanguageIdKey = stringPreferencesKey("sourceLanguageId")
  private val targetLanguageIdKey = stringPreferencesKey("targetLanguageId")

  val sourceLanguageId: Flow<String> =
    context.dataStore.data.map { preferences ->
      preferences[sourceLanguageIdKey] ?: ENGLISH_ID
    }

  val destLanguageId: Flow<String> =
    context.dataStore.data.map { preferences ->
      preferences[targetLanguageIdKey] ?: PORTUGUESE_ID
    }

  suspend fun updateSourceLanguageId(id: String) {
    context.dataStore.edit { preferences ->
      preferences[sourceLanguageIdKey] = id
    }
  }

  suspend fun updateTargetLanguageId(id: String) {
    context.dataStore.edit { preferences ->
      preferences[targetLanguageIdKey] = id
    }
  }

  private companion object {
    const val ENGLISH_ID = "d4d5d87e-c0a1-4f35-bb81-5f2709e628aa"
    const val PORTUGUESE_ID = "06305ea0-5ab9-4b2d-bebc-c99e93bc5d59"
  }
}

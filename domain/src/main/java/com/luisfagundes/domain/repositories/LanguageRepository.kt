package com.luisfagundes.domain.repositories

import com.luisfagundes.domain.models.Language

interface LanguageRepository {
  suspend fun listLanguages(): List<Language>
  suspend fun fetchLanguagePair(): Pair<Language, Language>
  suspend fun updateLanguage(
    id: String,
    isSourceLanguage: Boolean,
  )
}

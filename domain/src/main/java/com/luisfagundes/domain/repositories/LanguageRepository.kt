package com.luisfagundes.domain.repositories

import com.luisfagundes.domain.models.Language

interface LanguageRepository {
    suspend fun listLanguages(): List<Language>
    suspend fun getLanguagePair(
        sourceId: String,
        targetId: String
    ): Pair<Language, Language>
}
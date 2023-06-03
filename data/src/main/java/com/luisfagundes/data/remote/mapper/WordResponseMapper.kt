package com.luisfagundes.data.remote.mapper

import com.luisfagundes.data.remote.models.AlternateTranslationResponse
import com.luisfagundes.data.remote.models.BackTranslationResponse
import com.luisfagundes.data.remote.models.DictionaryLookupBodyResponse
import com.luisfagundes.data.remote.models.DictionaryLookupResponse
import com.luisfagundes.data.remote.models.TranslationResponse
import com.luisfagundes.domain.models.AlternateTranslation
import com.luisfagundes.domain.models.BackTranslation
import com.luisfagundes.domain.models.DictionaryLookup
import com.luisfagundes.domain.models.Translation

object WordResponseMapper {

    fun TranslationResponse.toDomain() = Translation(
        text = text,
        targetLanguage = to,
    )

    fun DictionaryLookupBodyResponse.toDomain() = map { it.toDomain() }

    fun DictionaryLookupResponse.toDomain() = DictionaryLookup(
        text = displaySource,
        alternateTranslations = translations.map { it.toDomain() },
    )

    fun AlternateTranslationResponse.toDomain() = AlternateTranslation(
        backTranslations = backTranslations.map { it.toDomain() },
        confidence = confidence,
        displayTarget = displayTarget,
        normalizedTarget = normalizedTarget,
        posTag = posTag,
        prefixWord = prefixWord,
    )

    fun BackTranslationResponse.toDomain() = BackTranslation(
        displayText = displayText,
        normalizedText = normalizedText,
        frequencyCount = frequencyCount,
        numExamples = numExamples,
    )
}

package com.luisfagundes.data.remote.models

data class AlternateTranslationResponse(
    val backTranslations: List<BackTranslationResponse>,
    val confidence: Double,
    val displayTarget: String,
    val normalizedTarget: String,
    val posTag: String,
    val prefixWord: String
)
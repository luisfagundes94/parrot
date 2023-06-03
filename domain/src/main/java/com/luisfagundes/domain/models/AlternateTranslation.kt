package com.luisfagundes.domain.models

data class AlternateTranslation(
    val backTranslations: List<BackTranslation>,
    val confidence: Double,
    val displayTarget: String,
    val normalizedTarget: String,
    val posTag: String,
    val prefixWord: String
)

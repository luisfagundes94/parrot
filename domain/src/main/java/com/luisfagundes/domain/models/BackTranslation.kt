package com.luisfagundes.domain.models

data class BackTranslation(
    val displayText: String,
    val frequencyCount: Int,
    val normalizedText: String,
    val numExamples: Int,
)

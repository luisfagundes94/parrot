package com.luisfagundes.data.remote.models

data class BackTranslationResponse(
    val displayText: String,
    val frequencyCount: Int,
    val normalizedText: String,
    val numExamples: Int
)
package com.luisfagundes.data.remote.models

data class DictionaryLookupResponse(
    val displaySource: String,
    val normalizedSource: String,
    val translations: List<AlternateTranslationResponse>
)
package com.luisfagundes.domain.models

data class DictionaryLookup(
    val text: String,
    val alternateTranslations: List<AlternateTranslation>,
)

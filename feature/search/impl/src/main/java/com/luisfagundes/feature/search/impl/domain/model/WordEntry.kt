package com.luisfagundes.feature.search.impl.domain.model

internal data class WordEntry(
    val word: String,
    val phonetic: String?,
    val audioUrl: String?,
    val meanings: List<Meaning>,
)

internal data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>,
    val synonyms: List<String>,
    val antonyms: List<String>,
)

internal data class Definition(
    val text: String,
    val example: String?,
)

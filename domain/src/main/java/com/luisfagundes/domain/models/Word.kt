package com.luisfagundes.domain.models

data class Word(
    val id: Int,
    val text: String,
    val translatedText: String,
    val dictionaryLookup: List<DictionaryLookup>,
) {
    fun doesMatchSearch(query: String): Boolean {
        val matchingCombinations = listOf(
            "$text $translatedText",
            "$text$translatedText",
            "${text.first()} ${translatedText.first()}",
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

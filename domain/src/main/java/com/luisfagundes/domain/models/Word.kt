package com.luisfagundes.domain.models

data class Word(
    val id: Int,
    val audioLinks: List<AudioLink>,
    val featured: Boolean,
    val type: String,
    val text: String,
    val translations: List<Translation>,
) {
    fun doesMatchSearch(query: String): Boolean {
        val translatedText = translations.first().text
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

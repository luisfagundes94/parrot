package com.luisfagundes.domain.models

data class Language(
    val id: String = "",
    val name: String,
    val nativeName: String,
    val code: String,
) {
    fun doesMatchSearch(query: String): Boolean {
        val matchingCombinations = listOf(
            "$name $nativeName",
            "$name$nativeName",
            "${name.first()} ${nativeName.first()}",
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

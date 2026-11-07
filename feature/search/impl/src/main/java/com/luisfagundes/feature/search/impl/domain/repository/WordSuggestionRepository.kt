package com.luisfagundes.feature.search.impl.domain.repository

internal interface WordSuggestionRepository {
    /** Returns up to [limit] English words starting with [prefix], in alphabetical order. */
    suspend fun suggest(prefix: String, limit: Int = DEFAULT_LIMIT): List<String>

    companion object {
        const val DEFAULT_LIMIT = 10
    }
}

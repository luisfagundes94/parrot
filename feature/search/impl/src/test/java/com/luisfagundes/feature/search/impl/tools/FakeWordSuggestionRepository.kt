package com.luisfagundes.feature.search.impl.tools

import com.luisfagundes.feature.search.impl.domain.repository.WordSuggestionRepository

internal class FakeWordSuggestionRepository : WordSuggestionRepository {
    override suspend fun suggest(prefix: String, limit: Int): List<String> = emptyList()
}

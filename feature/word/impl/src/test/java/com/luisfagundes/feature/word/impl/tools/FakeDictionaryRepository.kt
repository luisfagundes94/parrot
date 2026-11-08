package com.luisfagundes.feature.word.impl.tools

import com.luisfagundes.core.dictionary.domain.model.WordEntry
import com.luisfagundes.core.dictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal class FakeDictionaryRepository : DictionaryRepository {
    var result: Result<List<WordEntry>> = Result.success(emptyList())
    var searchCalls = 0

    override suspend fun search(word: String): Result<List<WordEntry>> {
        searchCalls++
        return result
    }

    override suspend fun getRecentlyLookedUpWords(): Flow<List<WordEntry>?> = emptyFlow()
}

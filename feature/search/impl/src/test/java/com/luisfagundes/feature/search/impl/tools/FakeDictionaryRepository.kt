package com.luisfagundes.feature.search.impl.tools

import com.luisfagundes.core.dictionary.domain.model.WordEntry
import com.luisfagundes.core.dictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal class FakeDictionaryRepository : DictionaryRepository {
    override suspend fun search(word: String): Result<List<WordEntry>> =
        Result.success(emptyList())

    override suspend fun getRecentlyLookedUpWords(): Flow<List<WordEntry>?> = emptyFlow()
}

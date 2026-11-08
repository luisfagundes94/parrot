package com.luisfagundes.core.dictionary.domain.repository

import com.luisfagundes.core.dictionary.domain.model.WordEntry
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    /**
     * Looks up [word] in the dictionary. Fails with
     * [com.luisfagundes.core.dictionary.domain.model.WordNotFoundException] when it has no entries.
     */
    suspend fun search(word: String): Result<List<WordEntry>>
    suspend fun getRecentlyLookedUpWords(): Flow<List<WordEntry>?>
}

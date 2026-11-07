package com.luisfagundes.feature.search.impl.domain.repository

import com.luisfagundes.feature.search.impl.domain.model.WordEntry

internal interface DictionaryRepository {
    /**
     * Looks up [word] in the dictionary. Fails with
     * [com.luisfagundes.feature.search.impl.domain.model.WordNotFoundException] when it has no entries.
     */
    suspend fun search(word: String): Result<List<WordEntry>>
}

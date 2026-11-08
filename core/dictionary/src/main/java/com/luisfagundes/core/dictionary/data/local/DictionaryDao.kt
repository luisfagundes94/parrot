package com.luisfagundes.core.dictionary.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/** How many looked-up words are kept; storing a new one beyond this evicts the oldest. */
internal const val MAX_STORED_WORDS = 5

@Dao
internal interface DictionaryDao {

    @Query("SELECT * FROM cached_words WHERE `query` = :query LIMIT 1")
    suspend fun getByQuery(query: String): CachedWordEntity?

    @Query("SELECT * FROM cached_words ORDER BY cachedAtMillis DESC LIMIT $MAX_STORED_WORDS")
    fun getRecentlyLookedUpWords(): Flow<List<CachedWordEntity>>

    @Upsert
    suspend fun upsert(entity: CachedWordEntity)

    /** Marks an already stored word as the most recently looked up one. */
    @Query("UPDATE cached_words SET cachedAtMillis = :timestampMillis WHERE `query` = :query")
    suspend fun touch(query: String, timestampMillis: Long)

    @Query(
        "DELETE FROM cached_words WHERE `query` NOT IN " +
            "(SELECT `query` FROM cached_words ORDER BY cachedAtMillis DESC LIMIT :limit)",
    )
    suspend fun deleteAllButNewest(limit: Int)

    /** Stores [entity] and evicts the oldest words so at most [MAX_STORED_WORDS] remain. */
    @Transaction
    suspend fun upsertKeepingNewest(entity: CachedWordEntity) {
        upsert(entity)
        deleteAllButNewest(MAX_STORED_WORDS)
    }
}

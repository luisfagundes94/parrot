package com.luisfagundes.feature.search.impl.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DictionaryDao {

    @Query("SELECT * FROM cached_words WHERE `query` = :query LIMIT 1")
    suspend fun getByQuery(query: String): CachedWordEntity?

    @Query("SELECT * FROM cached_words ORDER BY cachedAtMillis DESC LIMIT 5")
    fun getRecentlyLookedUpWords(): Flow<List<CachedWordEntity>>

    @Upsert
    suspend fun upsert(entity: CachedWordEntity)
}

package com.luisfagundes.feature.search.impl.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface DictionaryDao {

    @Query("SELECT * FROM cached_words WHERE `query` = :query LIMIT 1")
    suspend fun getByQuery(query: String): CachedWordEntity?

    @Upsert
    suspend fun upsert(entity: CachedWordEntity)
}

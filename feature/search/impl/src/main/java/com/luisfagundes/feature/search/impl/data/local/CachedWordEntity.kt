package com.luisfagundes.feature.search.impl.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/** A dictionary lookup cached as the JSON of the API response, keyed by the normalized query. */
@Entity(tableName = "cached_words")
internal data class CachedWordEntity(
    @PrimaryKey val query: String,
    val entriesJson: String,
    val cachedAtMillis: Long,
)

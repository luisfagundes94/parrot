package com.luisfagundes.core.dictionary.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luisfagundes.core.dictionary.data.model.WordEntryResponse
import com.luisfagundes.core.dictionary.data.model.toDomain
import com.luisfagundes.core.dictionary.domain.model.WordEntry
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

/** A dictionary lookup cached as the JSON of the API response, keyed by the normalized query. */
@Entity(tableName = "cached_words")
internal data class CachedWordEntity(
    @PrimaryKey val query: String,
    val entriesJson: String,
    val cachedAtMillis: Long,
)

private val entriesSerializer = ListSerializer(WordEntryResponse.serializer())

/** Decodes the cached API response into domain entries. Throws if the JSON is malformed. */
internal fun CachedWordEntity.toDomain(json: Json): List<WordEntry> =
    json.decodeFromString(entriesSerializer, entriesJson).map { it.toDomain() }

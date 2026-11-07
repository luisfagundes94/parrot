package com.luisfagundes.feature.search.impl.data.repository

import com.luisfagundes.core.common.di.IoDispatcher
import com.luisfagundes.core.common.tools.safeRunCatching
import com.luisfagundes.feature.search.impl.data.local.CachedWordEntity
import com.luisfagundes.feature.search.impl.data.local.DictionaryDao
import com.luisfagundes.feature.search.impl.data.local.toDomain
import com.luisfagundes.feature.search.impl.data.model.WordEntryResponse
import com.luisfagundes.feature.search.impl.data.model.toDomain
import com.luisfagundes.feature.search.impl.data.remote.DictionaryApi
import com.luisfagundes.feature.search.impl.domain.model.WordEntry
import com.luisfagundes.feature.search.impl.domain.model.WordNotFoundException
import com.luisfagundes.feature.search.impl.domain.repository.DictionaryRepository
import java.net.HttpURLConnection
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import retrofit2.HttpException

/**
 * Offline-first: a word that was looked up before is served from Room without touching the network.
 * Only unseen words hit the API, and successful responses are cached for next time.
 */
internal class DictionaryRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: DictionaryDao,
    private val json: Json,
    private val ioDispatcher: CoroutineDispatcher,
    private val clock: () -> Long,
) : DictionaryRepository {

    @Inject
    constructor(
        api: DictionaryApi,
        dao: DictionaryDao,
        json: Json,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) : this(api, dao, json, ioDispatcher, System::currentTimeMillis)

    override suspend fun search(word: String): Result<List<WordEntry>> {
        val query = word.trim()
        if (query.isEmpty()) return Result.failure(WordNotFoundException(word))
        val key = query.lowercase(Locale.ROOT)

        return withContext(ioDispatcher) {
            // A broken cache must not block a lookup; fall through to the network.
            val cached = safeRunCatching { dao.getByQuery(key)?.decode() }.getOrNull()
            if (cached != null) {
                Result.success(cached.map { it.toDomain() })
            } else {
                fetchAndCache(query, key)
            }
        }
    }

    private suspend fun fetchAndCache(query: String, key: String): Result<List<WordEntry>> =
        safeRunCatching {
            val entries = api.getEntries(query)
            // A failing cache write must not fail a lookup that already succeeded.
            safeRunCatching { dao.upsert(entries.toEntity(key)) }
            entries.map { it.toDomain() }
        }.recoverCatching { error ->
            if (error is HttpException && error.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                throw WordNotFoundException(query)
            }
            throw error
        }

    override suspend fun getRecentlyLookedUpWords(): Flow<List<WordEntry>?> =
        dao.getRecentlyLookedUpWords()
            .map { cached ->
                cached.mapNotNull { entity ->
                    safeRunCatching { entity.toDomain(json).firstOrNull() }.getOrNull()
                }
            }
            .flowOn(ioDispatcher)

    private fun List<WordEntryResponse>.toEntity(key: String) = CachedWordEntity(
        query = key,
        entriesJson = json.encodeToString(responseSerializer, this),
        cachedAtMillis = clock(),
    )

    private fun CachedWordEntity.decode(): List<WordEntryResponse> =
        json.decodeFromString(responseSerializer, entriesJson)

    private companion object {
        val responseSerializer = ListSerializer(WordEntryResponse.serializer())
    }
}

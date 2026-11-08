package com.luisfagundes.core.dictionary.data.repository

import com.luisfagundes.core.dictionary.data.local.CachedWordEntity
import com.luisfagundes.core.dictionary.data.local.DictionaryDao
import com.luisfagundes.core.dictionary.data.remote.DictionaryApi
import com.luisfagundes.core.dictionary.domain.model.WordNotFoundException
import com.luisfagundes.core.dictionary.domain.model.Definition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.MediaType.Companion.toMediaType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

internal class DictionaryRepositoryImplTest {

    private val server = MockWebServer()
    private val json = Json { ignoreUnknownKeys = true }
    private val dao = FakeDictionaryDao()
    private var now = 42L

    private lateinit var repository: DictionaryRepositoryImpl

    @BeforeEach
    fun setUp() {
        server.start()
        val api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(DictionaryApi::class.java)
        repository = DictionaryRepositoryImpl(api, dao, json, Dispatchers.Unconfined, clock = { now })
    }

    @AfterEach
    fun tearDown() {
        server.close()
    }

    @Test
    fun `search maps a successful response to domain`() = runTest {
        server.enqueue(MockResponse.Builder().code(200).body(HELLO_JSON).build())

        val result = repository.search("  hello ")

        val entry = result.getOrThrow().single()
        assertEquals("hello", entry.word)
        assertEquals("/həˈloʊ/", entry.phonetic)
        assertEquals("https://audio/hello.mp3", entry.audioUrl)
        assertEquals("interjection", entry.meanings.single().partOfSpeech)
        assertEquals(
            listOf(Definition("A greeting.", "Hello, world!")),
            entry.meanings.single().definitions,
        )
        assertEquals(listOf("hi"), entry.meanings.single().synonyms)
        assertEquals("/api/v2/entries/en/hello", server.takeRequest().url.encodedPath)
    }

    @Test
    fun `search turns a protocol-relative audio url into https`() = runTest {
        server.enqueue(
            MockResponse.Builder().code(200)
                .body("""[{"word":"x","phonetics":[{"audio":"//ssl.gstatic.com/x.mp3"}],"meanings":[]}]""")
                .build(),
        )

        val entry = repository.search("x").getOrThrow().single()

        assertEquals("https://ssl.gstatic.com/x.mp3", entry.audioUrl)
    }

    @Test
    fun `search fails with WordNotFoundException on 404`() = runTest {
        server.enqueue(
            MockResponse.Builder().code(404)
                .body("""{"title":"No Definitions Found","message":"","resolution":""}""")
                .build(),
        )

        val result = repository.search("asdfgh")

        assertInstanceOf(WordNotFoundException::class.java, result.exceptionOrNull())
    }

    @Test
    fun `search propagates other http errors`() = runTest {
        server.enqueue(MockResponse.Builder().code(500).build())

        val result = repository.search("hello")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() !is WordNotFoundException)
    }

    @Test
    fun `search with blank word fails without calling the api`() = runTest {
        val result = repository.search("   ")

        assertInstanceOf(WordNotFoundException::class.java, result.exceptionOrNull())
        assertEquals(0, server.requestCount)
    }

    @Test
    fun `search tolerates missing optional fields`() = runTest {
        server.enqueue(
            MockResponse.Builder().code(200)
                .body("""[{"word":"x","phonetics":[{}],"meanings":[]}]""")
                .build(),
        )

        val entry = repository.search("x").getOrThrow().single()

        assertNull(entry.phonetic)
        assertNull(entry.audioUrl)
    }

    @Test
    fun `search caches a successful response`() = runTest {
        server.enqueue(MockResponse.Builder().code(200).body(HELLO_JSON).build())

        repository.search("Hello")

        assertEquals(42L, dao.store["hello"]?.cachedAtMillis)
    }

    @Test
    fun `search serves a cached word without calling the api`() = runTest {
        server.enqueue(MockResponse.Builder().code(200).body(HELLO_JSON).build())
        repository.search("hello")

        val second = repository.search("HELLO").getOrThrow()

        assertEquals("hello", second.single().word)
        assertEquals(1, server.requestCount)
    }

    @Test
    fun `search works offline for a cached word`() = runTest {
        server.enqueue(MockResponse.Builder().code(200).body(HELLO_JSON).build())
        repository.search("hello")
        server.close()

        val result = repository.search("hello")

        assertEquals("hello", result.getOrThrow().single().word)
    }

    @Test
    fun `search fails when offline and not cached`() = runTest {
        server.close()

        val result = repository.search("hello")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() !is WordNotFoundException)
    }

    @Test
    fun `search does not cache a 404`() = runTest {
        server.enqueue(MockResponse.Builder().code(404).body("{}").build())

        repository.search("asdfgh")

        assertTrue(dao.store.isEmpty())
    }

    @Test
    fun `search still succeeds when the cache write fails`() = runTest {
        dao.failOnUpsert = true
        server.enqueue(MockResponse.Builder().code(200).body(HELLO_JSON).build())

        val result = repository.search("hello")

        assertEquals("hello", result.getOrThrow().single().word)
    }

    @Test
    fun `search keeps only the 5 most recent words and evicts the oldest`() = runTest {
        val words = listOf("one", "two", "three", "four", "five", "six")
        words.forEach { word ->
            server.enqueue(MockResponse.Builder().code(200).body(wordJson(word)).build())
            now += 1
            repository.search(word)
        }

        assertEquals(words.drop(1).toSet(), dao.store.keys)
    }

    @Test
    fun `search refreshes a cached word so it is evicted last`() = runTest {
        val words = listOf("one", "two", "three", "four", "five")
        words.forEach { word ->
            server.enqueue(MockResponse.Builder().code(200).body(wordJson(word)).build())
            now += 1
            repository.search(word)
        }
        now += 1
        repository.search("one")
        server.enqueue(MockResponse.Builder().code(200).body(wordJson("six")).build())
        now += 1
        repository.search("six")

        assertEquals(setOf("one", "three", "four", "five", "six"), dao.store.keys)
    }

    private fun wordJson(word: String) = """[{"word":"$word","phonetics":[],"meanings":[]}]"""

    private class FakeDictionaryDao : DictionaryDao {
        val store = mutableMapOf<String, CachedWordEntity>()
        var failOnUpsert = false

        override suspend fun getByQuery(query: String) = store[query]

        override fun getRecentlyLookedUpWords() = flowOf(store.values.toList())

        override suspend fun upsert(entity: CachedWordEntity) {
            if (failOnUpsert) error("disk full")
            store[entity.query] = entity
        }

        override suspend fun touch(query: String, timestampMillis: Long) {
            store[query]?.let { store[query] = it.copy(cachedAtMillis = timestampMillis) }
        }

        override suspend fun deleteAllButNewest(limit: Int) {
            val keep = store.values.sortedByDescending { it.cachedAtMillis }.take(limit).map { it.query }
            store.keys.retainAll(keep.toSet())
        }
    }

    private companion object {
        const val HELLO_JSON = """
            [{"word":"hello","phonetic":"/həˈloʊ/",
              "phonetics":[{"text":"/həˈloʊ/","audio":""},{"audio":"https://audio/hello.mp3"}],
              "meanings":[{"partOfSpeech":"interjection",
                "definitions":[{"definition":"A greeting.","example":"Hello, world!","synonyms":[],"antonyms":[]}],
                "synonyms":["hi"],"antonyms":[]}],
              "license":{"name":"CC","url":"x"},"sourceUrls":["x"]}]
        """
    }
}

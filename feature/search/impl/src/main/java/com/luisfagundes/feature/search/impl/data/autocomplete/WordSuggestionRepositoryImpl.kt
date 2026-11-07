package com.luisfagundes.feature.search.impl.data.autocomplete

import android.content.Context
import com.luisfagundes.feature.search.impl.domain.repository.WordSuggestionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

@Singleton
internal class WordSuggestionRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : WordSuggestionRepository {

    private val mutex = Mutex()
    private var index: SortedWordIndex? = null

    override suspend fun suggest(prefix: String, limit: Int): List<String> =
        loadIndex().suggest(prefix, limit)

    private suspend fun loadIndex(): SortedWordIndex = mutex.withLock {
        index ?: withContext(Dispatchers.IO) {
            context.assets.open(WORDS_ASSET).bufferedReader().useLines { lines ->
                SortedWordIndex(lines)
            }
        }.also { index = it }
    }

    private companion object {
        const val WORDS_ASSET = "english_words.txt"
    }
}

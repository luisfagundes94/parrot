package com.luisfagundes.feature.search.impl.presentation.viewmodel

import android.graphics.drawable.Drawable
import app.cash.turbine.test
import com.luisfagundes.core.common.tools.ResourceProvider
import com.luisfagundes.core.dictionary.domain.model.WordEntry
import com.luisfagundes.core.dictionary.domain.repository.DictionaryRepository
import com.luisfagundes.feature.search.impl.domain.repository.WordSuggestionRepository
import com.luisfagundes.feature.search.impl.presentation.effect.SearchEffect
import com.luisfagundes.feature.search.impl.presentation.event.SearchEvent
import com.luisfagundes.feature.search.impl.tools.MainDispatcherExtension
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

internal class SearchViewModelTest {

    @JvmField
    @RegisterExtension
    val mainDispatcher = MainDispatcherExtension()

    private val viewModel = SearchViewModel(
        repository = FakeDictionaryRepository(),
        suggestionRepository = FakeWordSuggestionRepository(),
        resourceProvider = FakeResourceProvider(),
    )

    @Test
    fun `OnWordClick navigates to the word detail`() = runTest {
        viewModel.uiEffect.test {
            // When
            viewModel.dispatchEvent(SearchEvent.OnWordClick("serendipity"))

            // Then
            assertEquals(SearchEffect.NavigateToWordDetail("serendipity"), awaitItem())
        }
    }

    @Test
    fun `OnSearchClick navigates to the trimmed word`() = runTest {
        viewModel.uiEffect.test {
            // When the user submits a query or taps a suggestion
            viewModel.dispatchEvent(SearchEvent.OnSearchClick("  parrot "))

            // Then
            assertEquals(SearchEffect.NavigateToWordDetail("parrot"), awaitItem())
        }
    }

    @Test
    fun `OnSearchClick with a blank query does not navigate`() = runTest {
        viewModel.uiEffect.test {
            // When
            viewModel.dispatchEvent(SearchEvent.OnSearchClick("   "))

            // Then
            expectNoEvents()
        }
    }

    private class FakeDictionaryRepository : DictionaryRepository {
        override suspend fun search(word: String): Result<List<WordEntry>> =
            Result.success(emptyList())

        override suspend fun getRecentlyLookedUpWords(): Flow<List<WordEntry>?> = emptyFlow()
    }

    private class FakeWordSuggestionRepository : WordSuggestionRepository {
        override suspend fun suggest(prefix: String, limit: Int): List<String> = emptyList()
    }

    private class FakeResourceProvider : ResourceProvider {
        override fun getString(resId: Int, vararg params: String) = ""
        override fun getColor(colorId: Int) = 0
        override fun getDrawable(drawableResId: Int): Drawable? = null
    }
}

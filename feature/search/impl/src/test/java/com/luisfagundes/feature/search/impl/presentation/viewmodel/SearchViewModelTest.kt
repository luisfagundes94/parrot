package com.luisfagundes.feature.search.impl.presentation.viewmodel

import app.cash.turbine.test
import com.luisfagundes.feature.search.impl.presentation.effect.SearchEffect
import com.luisfagundes.feature.search.impl.presentation.event.SearchEvent
import com.luisfagundes.feature.search.impl.tools.FakeDictionaryRepository
import com.luisfagundes.feature.search.impl.tools.FakeResourceProvider
import com.luisfagundes.feature.search.impl.tools.FakeWordSuggestionRepository
import com.luisfagundes.feature.search.impl.tools.MainDispatcherExtension
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
}

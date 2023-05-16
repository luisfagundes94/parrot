package com.luisfagundes.languages

import androidx.lifecycle.SavedStateHandle
import com.luisfagundes.commonsTesting.TestCoroutineRule
import com.luisfagundes.domain.modelFactory.LanguageFactory
import com.luisfagundes.domain.usecases.ListLanguages
import com.luisfagundes.domain.usecases.SaveWord
import com.luisfagundes.domain.usecases.UpdateLanguage
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LanguageListViewModelTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val getLanguageList: ListLanguages = mockk()
    private val updateLanguage: UpdateLanguage = mockk()
    private val savedWord: SaveWord = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private lateinit var viewModel: LanguageListViewModel

    @Before
    fun setup() {
        coEvery { savedStateHandle.get<Boolean>(any()) } returns true

        viewModel = LanguageListViewModel(
            savedStateHandle = savedStateHandle,
            getLanguageList = getLanguageList,
            updateLanguage = updateLanguage,
        )
    }

    @Test
    fun `onEvent GetLanguageList updates uiState with language list`() =
        coroutineRule.runTest {
            val languages = LanguageFactory.languages
            val expectedUiState = LanguageListUiState(
                languages = languages,
                isLoading = false,
                hasError = false,
            )
            coEvery { getLanguageList() } returns languages

            viewModel.onEvent(LanguageListEvent.GetLanguageList)

            coVerify(exactly = 1) { getLanguageList() }

            val uiState = viewModel.uiState.first()

            assertEquals(expectedUiState, uiState)
        }

    @Test
    fun `onEvent OnBackPressed does nothing`() = coroutineRule.runTest {
        viewModel.onEvent(LanguageListEvent.OnBackPressed)

        coVerify(exactly = 0) { getLanguageList() }
        coVerify(exactly = 0) { updateLanguage(any(), any()) }
    }

    @Test
    fun `onEvent OnLanguageClicked updates language`() = coroutineRule.runTest {
        val languageId = LanguageFactory.languages.random().id
        val isSourceLanguage = true

        coEvery { updateLanguage(any(), any()) } returns Unit

        viewModel.onEvent(
            LanguageListEvent.OnLanguageClicked(
                id = languageId,
                isSourceLanguage = isSourceLanguage,
            ),
        )

        coVerify(exactly = 1) { updateLanguage(languageId, isSourceLanguage) }
    }

    @Test
    fun `uiState should filter languages when searchText is not blank`() =
        coroutineRule.runTest {
            val searchText = "en"
            val languages = LanguageFactory.languages

            coEvery { getLanguageList() } returns languages

            viewModel.onEvent(LanguageListEvent.OnSearchTextChanged(searchText))

            val filteredLanguages = languages.filter { it.doesMatchSearch(searchText) }
            val result = listOf(LanguageFactory.languages.first())

            assertEquals(filteredLanguages, result)
        }
}

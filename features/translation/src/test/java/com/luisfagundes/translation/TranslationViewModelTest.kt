package com.luisfagundes.translation

import com.luisfagundes.commons_testing.TestCoroutineRule
import com.luisfagundes.domain.modelFactory.LanguageFactory
import com.luisfagundes.domain.modelFactory.WordFactory
import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.usecases.GetLanguagePair
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.domain.usecases.SaveWord
import com.luisfagundes.domain.usecases.ScheduleNotification
import com.luisfagundes.framework.network.DataState
import com.luisfagundes.provider.ResourceProvider
import com.luisfagundes.translation.presentation.TranslationEvent
import com.luisfagundes.translation.presentation.TranslationViewModel
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TranslationViewModelTest {
    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val getWordTranslations: GetWordTranslations = mockk()
    private val getLanguagePair: GetLanguagePair = mockk()
    private val saveWord: SaveWord = mockk()
    private val scheduleNotification: ScheduleNotification = mockk()
    private val appProvider: ResourceProvider = mockk()

    private val languagePair = Pair(
        LanguageFactory.languages.first(),
        LanguageFactory.languages.last(),
    )

    private lateinit var viewModel: TranslationViewModel

    @Before
    fun setUp() {
        viewModel = TranslationViewModel(
            getWordTranslations = getWordTranslations,
            getLanguagePair = getLanguagePair,
            saveWord = saveWord,
            scheduleNotification = scheduleNotification,
            appProvider = appProvider,
        )
    }

    @Test
    fun `onEvent Translate should call GetWordTranslations`() = coroutineRule.runTest {
        val text = "overload"

        val params = createTranslationParams(text, languagePair)
        val translatedWords = WordFactory.words
        val data = DataState.Success(translatedWords)

        coEvery { getLanguagePair() } returns languagePair
        coEvery { getWordTranslations(params) } returns data

        viewModel.onEvent(
            TranslationEvent.Translate(text),
        )

        coVerify(exactly = 1) { getWordTranslations(params) }
    }

    @Test
    fun `onEvent Translate SHOULD not call translation WHEN text length is less than 2`() = coroutineRule.runTest {
        val text = "a"

        val params = createTranslationParams(text, languagePair)
        val data = DataState.Empty

        coEvery { getLanguagePair() } returns languagePair
        coEvery { getWordTranslations(params) } returns data

        viewModel.onEvent(
            TranslationEvent.Translate(text),
        )

        coVerify { getWordTranslations(params) wasNot Called }
    }

    @Test
    @Throws(Exception::class)
    fun `onEvent Translate SHOULD update error state WHEN text doesn't exist`() = coroutineRule.runTest {
        val text = "r2kpflajdsoi"

        val params = createTranslationParams(text, languagePair)
        val data = DataState.Error(Throwable("error"))

        coEvery { getLanguagePair() } returns languagePair
        coEvery { getWordTranslations(params) } returns data

        viewModel.onEvent(
            TranslationEvent.Translate(text),
        )

        assert(viewModel.uiState.first().hasError)
    }

    @Test
    fun `onEvent Translate should update uiState with translatedText`() = coroutineRule.runTest {
        val text = "overload"
        val expectedText = "sobrecarga"

        val params = createTranslationParams(text, languagePair)
        val translatedWords = WordFactory.words
        val data = DataState.Success(translatedWords)

        coEvery { getLanguagePair() } returns languagePair
        coEvery { getWordTranslations(params) } returns data

        viewModel.onEvent(
            TranslationEvent.Translate(text),
        )

        val uiState = viewModel.uiState.first()
        val translatedText = uiState.wordList.first().translations.first().text

        assertEquals(translatedText, expectedText)
    }

    @Test
    fun `onEvent InvertLanguages should correctly update languagePair state`() = coroutineRule.runTest {
        val languagePairInput = Pair(
            LanguageFactory.languages.first(),
            LanguageFactory.languages.last(),
        )
        val expectedLanguagePair = Pair(
            LanguageFactory.languages.last(),
            LanguageFactory.languages.first(),
        )

        viewModel.onEvent(
            TranslationEvent.InvertLanguagePair(languagePairInput),
        )

        val languagePair = viewModel.uiState.first().languagePair

        assertEquals(languagePair, expectedLanguagePair)
    }

    @Test
    fun `onEvent InvertLanguages should not change uiState if input is null`() = coroutineRule.runTest {
        val initialUiState = viewModel.uiState.value

        viewModel.onEvent(TranslationEvent.InvertLanguagePair(null))

        val finalUiState = viewModel.uiState.value

        assertEquals(initialUiState, finalUiState)
    }

    private fun createTranslationParams(
        text: String,
        languagePair: Pair<Language, Language>,
    ): GetWordTranslations.Params {
        val firstCode = languagePair.first.code
        val secondCode = languagePair.second.code

        return GetWordTranslations.Params(
            text = text,
            sourceLanguage = firstCode,
            destLanguage = secondCode,
        )
    }
}

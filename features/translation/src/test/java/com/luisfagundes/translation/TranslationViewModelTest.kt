package com.luisfagundes.translation

import android.graphics.Bitmap
import app.cash.turbine.test
import com.luisfagundes.commonsTesting.TestCoroutineRule
import com.luisfagundes.domain.modelFactory.LanguageFactory
import com.luisfagundes.domain.modelFactory.WordFactory
import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.models.ScheduleData
import com.luisfagundes.domain.usecases.GetLanguagePair
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.domain.usecases.SaveWord
import com.luisfagundes.domain.usecases.ScheduleNotification
import com.luisfagundes.framework.network.DataState
import com.luisfagundes.provider.ResourceProvider
import com.luisfagundes.translation.presentation.TranslationEvent
import com.luisfagundes.translation.presentation.TranslationViewModel
import io.mockk.Called
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TranslationViewModelTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val getWordTranslations: GetWordTranslations = mockk()
    private val getLanguagePair: GetLanguagePair = mockk()
    private val saveWord: SaveWord = mockk()
    private val scheduleNotification: ScheduleNotification = mockk()
    private val appProvider: ResourceProvider = mockk()

    private val bitmap: Bitmap = mockk()

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
            dispatcher = Dispatchers.IO,
        )
    }

    @Test
    fun `onEvent Translate should call GetWordTranslations`() = runTest {
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
    fun `onEvent Translate SHOULD not call translation WHEN text length is less than 2`() =
        runTest {
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
    fun `onEvent Translate SHOULD update error state WHEN text doesn't exist`() =
        runTest {
            val text = "r2kpflajdsoi"

            val params = createTranslationParams(text, languagePair)
            val data = DataState.Error(Throwable("error"))

            coEvery { getLanguagePair() } returns languagePair
            coEvery { getWordTranslations(params) } returns data

            viewModel.onEvent(
                TranslationEvent.Translate(text),
            )

            viewModel.uiState.test {
                awaitItem().apply {
                    assert(this.hasError)
                }
            }
        }

    @Test
    fun `onEvent Translate should update uiState with translatedText`() = runTest {
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

        viewModel.uiState.test {
            awaitItem().apply {
                val translatedText = this.wordList.first().translations.first().text
                assertEquals(translatedText, expectedText)
            }
        }
    }

    @Test
    fun `onEvent InvertLanguages should correctly update languagePair state`() =
        runTest {
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

            viewModel.uiState.test {
                awaitItem().apply {
                    val languagePair = this.languagePair
                    assertEquals(languagePair, expectedLanguagePair)
                }
            }
        }

    @Test
    fun `onEvent InvertLanguages should not change uiState if input is null`() =
        runTest {
            val initialUiState = viewModel.uiState.value

            viewModel.onEvent(TranslationEvent.InvertLanguagePair(null))

            viewModel.uiState.test {
                awaitItem().apply {
                    assertEquals(this, initialUiState)
                }
            }
        }

    @Test
    fun `onEvent UpdateLanguagePair SHOULD update uiState with new languagePair`() =
        runTest {
            val expectedLanguagePair = Pair(
                LanguageFactory.languages.first(),
                LanguageFactory.languages.last(),
            )

            coEvery { getLanguagePair() } returns expectedLanguagePair

            viewModel.onEvent(
                TranslationEvent.UpdateLanguagePair,
            )

            viewModel.uiState.test {
                awaitItem().apply {
                    assertEquals(this.languagePair, expectedLanguagePair)
                }
            }
        }

    @Test
    fun `onEvent SaveWord should call useCase`() = runTest {
        val scheduleData = ScheduleData(intervalHours = 2)
        val word = WordFactory.words.first()

        coEvery { saveWord(word) } returns DataState.Success(Unit)

        viewModel.onEvent(
            TranslationEvent.SaveWord(scheduleData, word),
        )

        coVerify(exactly = 1) { saveWord(word) }
    }

    @Test
    fun `onEvent SaveWord should update schedule a notification`() = runTest {
        val scheduleData = ScheduleData(intervalHours = 2)
        val word = WordFactory.words.first()

        coEvery { saveWord(word) } returns DataState.Success(Unit)
        coEvery { appProvider.getAppIconId() } returns 1
        coEvery { appProvider.getAppIconBitmap() } returns bitmap
        coEvery { scheduleNotification(scheduleData, any()) } just Runs

        viewModel.onEvent(
            TranslationEvent.SaveWord(scheduleData, word),
        )

        coVerify(exactly = 1) { scheduleNotification(scheduleData, any()) }
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

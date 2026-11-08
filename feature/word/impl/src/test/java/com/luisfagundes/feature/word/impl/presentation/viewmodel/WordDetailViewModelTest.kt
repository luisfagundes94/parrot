package com.luisfagundes.feature.word.impl.presentation.viewmodel

import app.cash.turbine.test
import com.luisfagundes.core.dictionary.domain.model.Definition
import com.luisfagundes.core.dictionary.domain.model.Meaning
import com.luisfagundes.core.dictionary.domain.model.WordEntry
import com.luisfagundes.core.dictionary.domain.model.WordNotFoundException
import com.luisfagundes.feature.word.impl.presentation.effect.WordDetailEffect
import com.luisfagundes.feature.word.impl.presentation.event.WordDetailEvent
import com.luisfagundes.feature.word.impl.presentation.state.MeaningUi
import com.luisfagundes.feature.word.impl.presentation.state.WordDetailState
import com.luisfagundes.feature.word.impl.tools.FakeDictionaryRepository
import com.luisfagundes.feature.word.impl.tools.FakeResourceProvider
import com.luisfagundes.feature.word.impl.tools.FakeWordSpeaker
import com.luisfagundes.feature.word.impl.tools.MainDispatcherExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

internal class WordDetailViewModelTest {

    @JvmField
    @RegisterExtension
    val mainDispatcher = MainDispatcherExtension()

    private val repository = FakeDictionaryRepository()
    private val wordSpeaker = FakeWordSpeaker()
    private val viewModel = WordDetailViewModel(repository, wordSpeaker, FakeResourceProvider())

    @Test
    fun `starts in loading state`() {
        // Given a freshly created view model
        // Then
        assertEquals(WordDetailState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `OnLoad merges entries into content`() {
        // Given two entries for the same word
        repository.result = Result.success(
            listOf(
                entry(phonetic = "/ˌser/", meanings = listOf(noun)),
                entry(phonetic = "/other/", meanings = listOf(verb)),
            )
        )

        // When
        viewModel.dispatchEvent(WordDetailEvent.OnLoad("serendipity"))

        // Then
        val content = viewModel.uiState.value as WordDetailState.Content
        assertEquals("serendipity", content.word)
        assertEquals("/ˌser/", content.phonetic)
        assertEquals(
            listOf(
                MeaningUi("noun", "First definition.", listOf("Example one.", "Example two.")),
                MeaningUi("verb", "To do.", emptyList()),
            ),
            content.meanings,
        )
        assertFalse(content.isBookmarked)
        assertFalse(content.isPlayingAudio)
    }

    @Test
    fun `OnLoad drops meanings without a definition`() {
        // Given
        val empty = Meaning("adverb", listOf(Definition("  ", null)), emptyList(), emptyList())
        repository.result = Result.success(listOf(entry(meanings = listOf(empty, noun))))

        // When
        viewModel.dispatchEvent(WordDetailEvent.OnLoad("serendipity"))

        // Then
        val content = viewModel.uiState.value as WordDetailState.Content
        assertEquals(listOf("noun"), content.meanings.map { it.partOfSpeech })
    }

    @Test
    fun `OnLoad shows a not found error when the word is unknown`() {
        // Given
        repository.result = Result.failure(WordNotFoundException("asdfgh"))

        // When
        viewModel.dispatchEvent(WordDetailEvent.OnLoad("asdfgh"))

        // Then
        val error = viewModel.uiState.value as WordDetailState.Error
        assertTrue(error.message.contains("asdfgh"))
    }

    @Test
    fun `OnLoad shows a generic error on other failures and can retry`() {
        // Given
        repository.result = Result.failure(RuntimeException("boom"))
        viewModel.dispatchEvent(WordDetailEvent.OnLoad("serendipity"))
        assertInstanceOf(WordDetailState.Error::class.java, viewModel.uiState.value)

        // When the user retries and the lookup now works
        repository.result = Result.success(listOf(entry(meanings = listOf(noun))))
        viewModel.dispatchEvent(WordDetailEvent.OnLoad("serendipity"))

        // Then
        assertInstanceOf(WordDetailState.Content::class.java, viewModel.uiState.value)
        assertEquals(2, repository.searchCalls)
    }

    @Test
    fun `OnLoad with the same word does not reload content`() {
        // Given
        repository.result = Result.success(listOf(entry(meanings = listOf(noun))))
        viewModel.dispatchEvent(WordDetailEvent.OnLoad("serendipity"))
        viewModel.dispatchEvent(WordDetailEvent.OnBookmarkClick)

        // When the screen re-sends OnLoad (e.g. after rotation)
        viewModel.dispatchEvent(WordDetailEvent.OnLoad("serendipity"))

        // Then the bookmark state is kept and no second lookup happens
        assertTrue((viewModel.uiState.value as WordDetailState.Content).isBookmarked)
        assertEquals(1, repository.searchCalls)
    }

    @Test
    fun `OnBookmarkClick toggles the bookmark`() {
        // Given
        repository.result = Result.success(listOf(entry(meanings = listOf(noun))))
        viewModel.dispatchEvent(WordDetailEvent.OnLoad("serendipity"))

        // When
        viewModel.dispatchEvent(WordDetailEvent.OnBookmarkClick)
        val afterFirstClick = (viewModel.uiState.value as WordDetailState.Content).isBookmarked
        viewModel.dispatchEvent(WordDetailEvent.OnBookmarkClick)
        val afterSecondClick = (viewModel.uiState.value as WordDetailState.Content).isBookmarked

        // Then
        assertTrue(afterFirstClick)
        assertFalse(afterSecondClick)
    }

    @Test
    fun `OnPlayAudioClick speaks the word and resets when it finishes`() {
        // Given
        loadWord()

        // When speech starts
        viewModel.dispatchEvent(WordDetailEvent.OnPlayAudioClick)

        // Then
        assertEquals(listOf("serendipity"), wordSpeaker.spoken)
        assertTrue((viewModel.uiState.value as WordDetailState.Content).isPlayingAudio)

        // When it completes
        wordSpeaker.finish(Result.success(Unit))

        // Then
        assertFalse((viewModel.uiState.value as WordDetailState.Content).isPlayingAudio)
    }

    @Test
    fun `OnPlayAudioClick while speaking stops the speech`() {
        // Given
        loadWord()
        viewModel.dispatchEvent(WordDetailEvent.OnPlayAudioClick)

        // When
        viewModel.dispatchEvent(WordDetailEvent.OnPlayAudioClick)

        // Then
        assertFalse((viewModel.uiState.value as WordDetailState.Content).isPlayingAudio)
        assertEquals(1, wordSpeaker.spoken.size)
    }

    @Test
    fun `OnPlayAudioClick resets the flag when speech fails`() {
        // Given
        loadWord()
        viewModel.dispatchEvent(WordDetailEvent.OnPlayAudioClick)

        // When
        wordSpeaker.finish(Result.failure(IllegalStateException("no voice installed")))

        // Then
        assertFalse((viewModel.uiState.value as WordDetailState.Content).isPlayingAudio)
    }

    @Test
    fun `OnPlayAudioClick before the word is loaded does nothing`() {
        // When
        viewModel.dispatchEvent(WordDetailEvent.OnPlayAudioClick)

        // Then
        assertTrue(wordSpeaker.spoken.isEmpty())
    }

    @Test
    fun `OnSetReminderClick navigates back`() = runTestWithEffect(WordDetailEvent.OnSetReminderClick)

    @Test
    fun `OnBackClick navigates back`() = runTestWithEffect(WordDetailEvent.OnBackClick)

    private fun runTestWithEffect(event: WordDetailEvent) = kotlinx.coroutines.test.runTest {
        viewModel.uiEffect.test {
            // When
            viewModel.dispatchEvent(event)

            // Then
            assertEquals(WordDetailEffect.NavigateBack, awaitItem())
        }
    }

    private fun loadWord() {
        repository.result = Result.success(listOf(entry(meanings = listOf(noun))))
        viewModel.dispatchEvent(WordDetailEvent.OnLoad("serendipity"))
    }

    private fun entry(
        phonetic: String? = null,
        meanings: List<Meaning>,
    ) = WordEntry("serendipity", phonetic, audioUrl = null, meanings = meanings)

    private val noun = Meaning(
        partOfSpeech = "noun",
        definitions = listOf(
            Definition("First definition.", "Example one."),
            Definition("Second definition.", "Example two."),
            Definition("Third definition.", "Example one."),
            Definition("Fourth definition.", " "),
        ),
        synonyms = emptyList(),
        antonyms = emptyList(),
    )

    private val verb = Meaning(
        partOfSpeech = "verb",
        definitions = listOf(Definition("To do.", null)),
        synonyms = emptyList(),
        antonyms = emptyList(),
    )
}

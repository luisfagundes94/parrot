package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.models.NotificationData
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.GetLanguagePair
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.domain.usecases.SaveWord
import com.luisfagundes.domain.usecases.ScheduleNotification
import com.luisfagundes.framework.base.BaseViewModel
import com.luisfagundes.framework.base.IoDispatcher
import com.luisfagundes.framework.base.SingleEvent
import com.luisfagundes.framework.network.DataState
import com.luisfagundes.framework.utils.doNothing
import com.luisfagundes.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val NOTIFICATION_ID = 1
private const val ZERO = 0

@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val getWordTranslations: GetWordTranslations,
    private val getLanguagePair: GetLanguagePair,
    private val saveWord: SaveWord,
    private val scheduleNotification: ScheduleNotification,
    private val appProvider: ResourceProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(TranslationUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: TranslationEvent) = safeLaunch {
        when (event) {
            is TranslationEvent.Translate -> translateWord(event.text)
            is TranslationEvent.InvertLanguagePair -> invertLanguages(event.languagePair)
            is TranslationEvent.UpdateLanguagePair -> updateLanguagePair()
            is TranslationEvent.OnLanguageClicked -> doNothing()
            is TranslationEvent.SaveWord -> handleSaveWord(event)
        }
    }

    private fun translateWord(text: String) = safeLaunch {
        if (text.length < 2) return@safeLaunch

        val params = createTranslationParams(text, getLanguagePair())

        startLoading()

        val result = getWordTranslations(params)
        handleResult(result) { words ->
            _uiState.update { it.toSuccessState(words) }
        }
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

    private fun invertLanguages(languagePair: Pair<Language, Language>?) {
        if (languagePair == null) return

        _uiState.update {
            it.copy(
                languagePair = Pair(languagePair.second, languagePair.first),
            )
        }
    }

    private fun updateLanguagePair() = safeLaunch {
        _uiState.update {
            it.copy(languagePair = getLanguagePair())
        }
    }
    private fun handleSaveWord(event: TranslationEvent.SaveWord) = safeLaunch {
        val isWordSavedSuccessfully = withContext(dispatcher) {
            saveWord(event.word) is DataState.Success
        }
        updateWordSavedEvent(isWordSavedSuccessfully)
        if (isWordSavedSuccessfully) scheduleNotificationAlarm(event)
    }

    private fun scheduleNotificationAlarm(
        event: TranslationEvent.SaveWord,
    ) {
        if (event.scheduleData.intervalHours < ZERO) return
        val notificationData = createNotificationData(event.word)
        scheduleNotification(event.scheduleData, notificationData)
    }

    private fun createNotificationData(word: Word) = NotificationData(
        id = NOTIFICATION_ID,
        smallIconId = appProvider.getAppIconId(),
        largeIcon = appProvider.getAppIconBitmap(),
        title = word.text,
        content = word.translations.first().text,
    )

    private fun updateWordSavedEvent(
        isSuccessful: Boolean,
    ) {
        _uiState.update {
            it.copy(wordSavedEvent = SingleEvent(isSuccessful))
        }
    }

    override fun startLoading() {
        _uiState.update { it.toLoadingState() }
    }

    override fun handleEmpty() {
        _uiState.update { it.toEmptyState() }
    }

    override fun handleError(exception: Throwable) {
        println(exception.stackTraceToString())
        _uiState.update { it.toErrorState() }
    }
}

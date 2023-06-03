package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.models.NotificationData
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.GetLanguagePair
import com.luisfagundes.domain.usecases.TranslateText
import com.luisfagundes.domain.usecases.SaveWord
import com.luisfagundes.domain.usecases.ScheduleNotification
import com.luisfagundes.framework.base.IoDispatcher
import com.luisfagundes.framework.base.SingleEvent
import com.luisfagundes.framework.base.BaseViewState
import com.luisfagundes.framework.base.MviViewModel
import com.luisfagundes.framework.network.DataState
import com.luisfagundes.framework.utils.doNothing
import com.luisfagundes.provider.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val ZERO = 0

typealias LanguagePair = Pair<Language, Language>?

@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val translateText: TranslateText,
    private val getLanguagePair: GetLanguagePair,
    private val saveWord: SaveWord,
    private val scheduleNotification: ScheduleNotification,
    private val appProvider: ResourceProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : MviViewModel<BaseViewState<TranslationViewState>, TranslationEvent>() {

    override fun onEvent(event: TranslationEvent) = safeLaunch {
        when (event) {
            is TranslationEvent.Translate -> onTranslateWord(event.text)
            is TranslationEvent.InvertLanguagePair -> onInvertLanguagePair(event.languagePair)
            is TranslationEvent.UpdateLanguagePair -> {
                _wordState.update {
                    it.copy(languagePair = getLanguagePair())
                }
            }
            is TranslationEvent.OnLanguageClicked -> doNothing()
            is TranslationEvent.SaveWord -> {
                if (isWordSavedSuccessfully(event)) {
                    scheduleNotificationAlarm(event)
                    _wordState.update {
                        it.copy(wordSavedEvent = SingleEvent(true))
                    }
                }
            }
        }
    }

    private suspend fun isWordSavedSuccessfully(event: TranslationEvent.SaveWord) =
        withContext(dispatcher) {
            saveWord(event.word) is DataState.Success
        }

    private fun onTranslateWord(text: String) = safeLaunch {
        if (text.length < 2) return@safeLaunch

        val params = createTranslationParams(text, getLanguagePair())

        BaseViewState.Loading<Word>()

        execute(translateText(params)) { word ->
            _wordState.update {
                it.copy(
                    word = word,
                    wordSavedEvent = SingleEvent(false),
                )
            }
        }
    }

    private fun createTranslationParams(
        text: String,
        languagePair: Pair<Language, Language>,
    ): TranslateText.Params {
        val firstCode = languagePair.first.code
        val secondCode = languagePair.second.code

        return TranslateText.Params(
            text = text,
            sourceLanguage = firstCode,
            destLanguage = secondCode,
        )
    }

    private fun onInvertLanguagePair(languagePair: Pair<Language, Language>?) {
        if (languagePair == null) return

        setState(
            BaseViewState.Data(
                TranslationViewState(
                    languagePair = Pair(languagePair.second, languagePair.first),
                )
            )
        )
    }

    private fun scheduleNotificationAlarm(
        event: TranslationEvent.SaveWord,
    ) {
        if (event.scheduleData.intervalHours < ZERO) return
        val notificationData = createNotificationData(event.word)
        scheduleNotification(event.scheduleData, notificationData)
    }

    private fun createNotificationData(word: Word) = NotificationData(
        id = word.id,
        smallIconId = appProvider.getAppIconId(),
        largeIcon = appProvider.getAppIconBitmap(),
        title = word.text,
        content = word.translatedText,
    )
}

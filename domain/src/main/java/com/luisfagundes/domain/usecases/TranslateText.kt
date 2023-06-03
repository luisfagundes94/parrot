package com.luisfagundes.domain.usecases

import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.repositories.WordRepository
import com.luisfagundes.framework.network.DataState
import com.luisfagundes.framework.network.DataState.Error
import com.luisfagundes.framework.network.DataState.Success
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

@ViewModelScoped
class TranslateText @Inject constructor(
    private val repository: WordRepository,
) {
    data class Params(
        val text: String,
        val sourceLanguage: String,
        val destLanguage: String,
    )

    suspend operator fun invoke(params: Params): DataState<Word> = coroutineScope {
        val translationAsync = async { repository.translateText(params) }
        val dictionaryLookupAsync = async { repository.fetchDictionaryLookup(params) }

        val translationState = translationAsync.await()
        val dictionaryLookupState = dictionaryLookupAsync.await()

        try {
            val translation = translationState.getResultOrThrow()
            val dictionaryLookup = dictionaryLookupState.getResultOrThrow()
            return@coroutineScope Success(
                Word(
                    id = this.hashCode(),
                    text = params.text,
                    translatedText = translation.text,
                    dictionaryLookup = dictionaryLookup,
                )
            )
        } catch (exception: Exception) {
            return@coroutineScope Error(exception)
        }
    }
}

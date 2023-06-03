package com.luisfagundes.domain.repositories

import com.luisfagundes.domain.models.DictionaryLookup
import com.luisfagundes.domain.models.Translation
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.TranslateText
import com.luisfagundes.framework.network.DataState

interface WordRepository {
    suspend fun translateText(
        params: TranslateText.Params,
    ): DataState<Translation>

    suspend fun fetchDictionaryLookup(
        params: TranslateText.Params,
    ): DataState<List<DictionaryLookup>>

    suspend fun saveWord(word: Word): DataState<Unit>

    suspend fun getAllSavedWords(): List<Word>

    suspend fun deleteWord(word: Word): DataState<Unit>
}

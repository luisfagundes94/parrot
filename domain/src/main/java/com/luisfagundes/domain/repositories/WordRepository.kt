package com.luisfagundes.domain.repositories

import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.framework.network.DataState

interface WordRepository {
    suspend fun translateWord(
        params: GetWordTranslations.Params
    ): DataState<List<Word>>
}
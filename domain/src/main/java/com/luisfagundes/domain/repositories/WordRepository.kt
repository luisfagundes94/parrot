package com.luisfagundes.domain.repositories

import android.content.Context
import com.luisfagundes.domain.models.Word
import com.luisfagundes.framework.network.DataState

interface WordRepository {
    suspend fun translateWord(params: Map<String, String>): DataState<List<Word>>
}
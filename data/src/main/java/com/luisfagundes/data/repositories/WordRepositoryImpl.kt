package com.luisfagundes.data.repositories

import com.luisfagundes.data.mapper.WordMapper.toDomain
import com.luisfagundes.data.services.LingueeApiService
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.repositories.WordRepository
import com.luisfagundes.framework.network.DataState
import com.luisfagundes.framework.network.safeApiCall

class WordRepositoryImpl(
    private val lingueeApiService: LingueeApiService
) : WordRepository {
    override suspend fun translateWord(params: Map<String, String>): DataState<List<Word>> {
        return safeApiCall {
            lingueeApiService.fetchWordTranslations(params)
        }.map { wordResponseList ->
            wordResponseList.toDomain()
        }
    }
}
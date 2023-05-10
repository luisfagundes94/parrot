package com.luisfagundes.data.repositories

import com.luisfagundes.data.mapper.WordMapper.toDomain
import com.luisfagundes.data.services.LingueeApiService
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.repositories.WordRepository
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.framework.network.DataState
import com.luisfagundes.framework.network.safeApiCall

class WordRepositoryImpl(
    private val lingueeApiService: LingueeApiService
) : WordRepository {
    override suspend fun translateWord(params: GetWordTranslations.Params): DataState<List<Word>> {
        val queryMap = mapToQueryMap(params)

        return safeApiCall {
            lingueeApiService.fetchWordTranslations(queryMap)
        }.map { wordResponseList ->
            wordResponseList.toDomain()
        }
    }

    private fun mapToQueryMap(params: GetWordTranslations.Params) =
         mapOf(
            QUERY to params.text,
            SOURCE_LANGUAGE to params.sourceLanguage.lowercase(),
            DEST_LANGUAGE to params.destLanguage.lowercase()
        )

    private companion object {
        const val QUERY = "query"
        const val SOURCE_LANGUAGE = "src"
        const val DEST_LANGUAGE = "dst"
    }
}
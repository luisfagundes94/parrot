package com.luisfagundes.data.repositories

import com.luisfagundes.data.local.database.ParrotDatabase
import com.luisfagundes.data.local.mapper.DomainToEntityMapper.toEntity
import com.luisfagundes.data.local.mapper.EntityToDomainMapper.toDomain
import com.luisfagundes.data.remote.mapper.WordResponseMapper.toDomain
import com.luisfagundes.data.remote.services.LingueeApiService
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.repositories.WordRepository
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.framework.network.DataState
import com.luisfagundes.framework.network.safeApiCall

class WordRepositoryImpl(
    private val lingueeApiService: LingueeApiService,
    private val database: ParrotDatabase
) : WordRepository {
    override suspend fun translateWord(params: GetWordTranslations.Params): DataState<List<Word>> {
        val queryMap = mapToQueryMap(params)

        return safeApiCall {
            lingueeApiService.fetchWordTranslations(queryMap)
        }.map { wordResponseList ->
            wordResponseList.toDomain()
        }
    }

    override suspend fun saveWord(word: Word): DataState<Unit> {
        val wordDao = database.wordDao()
        val result = wordDao.insert(word.toEntity())

        return if (result == NO_ROWS_INSERTED) {
            DataState.Error(Exception("Error saving word"))
        } else {
            DataState.Success(Unit)
        }
    }

    override suspend fun getAllSavedWords(): List<Word> {
        val wordDao = database.wordDao()
        return wordDao.getAll().map { it.toDomain() }
    }

    override suspend fun deleteWord(word: Word): DataState<Unit> {
        val wordDao = database.wordDao()
        val result = wordDao.delete(word.toEntity())

        return if (result == NO_ROWS_DELETED) {
            DataState.Error(Exception("Error deleting word"))
        } else {
            DataState.Success(Unit)
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
        const val NO_ROWS_DELETED = 0
        const val NO_ROWS_INSERTED = -1L
    }
}
package com.luisfagundes.data.repositories

import com.luisfagundes.data.local.database.ParrotDatabase
import com.luisfagundes.data.local.mapper.DomainToEntityMapper.toEntity
import com.luisfagundes.data.local.mapper.EntityToDomainMapper.toDomain
import com.luisfagundes.data.remote.mapper.WordResponseMapper.toDomain
import com.luisfagundes.data.remote.models.TranslateRequestBody
import com.luisfagundes.data.remote.services.MicrosoftTranslateService
import com.luisfagundes.domain.models.Translation
import com.luisfagundes.domain.models.Word
import com.luisfagundes.domain.repositories.WordRepository
import com.luisfagundes.domain.usecases.TranslateText
import com.luisfagundes.framework.network.DataState
import com.luisfagundes.framework.network.safeApiCall
import timber.log.Timber

class WordRepositoryImpl(
    private val apiService: MicrosoftTranslateService,
    private val database: ParrotDatabase,
) : WordRepository {
    override suspend fun translateText(
        params: TranslateText.Params,
    ): DataState<Translation> {
        val queryMap = mapToQueryMap(params)
        val requestBody = TranslateRequestBody(params.text)

        return safeApiCall {
            apiService.translateText(queryMap, requestBody).translations
        }.map { translations ->
            translations.first().toDomain()
        }
    }

    override suspend fun fetchDictionaryLookup(params: TranslateText.Params) = safeApiCall {
        apiService.fetchDictionaryLookup(
            mapToQueryMap(params)
        ).toDomain()
    }

    override suspend fun saveWord(word: Word): DataState<Unit> {
        val wordDao = database.wordDao()
        val result = try {
            wordDao.insert(word.toEntity())
        } catch (exception: Exception) {
            Timber.d("Error saving word", exception)
            NO_ROWS_INSERTED
        }

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

    private fun mapToQueryMap(params: TranslateText.Params) =
        mapOf(
            API_VERSION to API_VERSION_VALUE,
            SOURCE_LANGUAGE to params.sourceLanguage.lowercase(),
            DEST_LANGUAGE to params.destLanguage.lowercase(),
        )

    private companion object {
        const val TEXT = "text"
        const val API_VERSION = "api-version="
        const val API_VERSION_VALUE = "3.0"
        const val SOURCE_LANGUAGE = "from"
        const val DEST_LANGUAGE = "to"
        const val NO_ROWS_DELETED = 0
        const val NO_ROWS_INSERTED = -1L
    }
}

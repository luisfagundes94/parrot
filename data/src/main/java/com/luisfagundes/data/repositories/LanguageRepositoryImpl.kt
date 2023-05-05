package com.luisfagundes.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.luisfagundes.data.store.LanguageDataStore
import com.luisfagundes.data.utils.getJsonDataFromAsset
import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.repositories.LanguageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class LanguageRepositoryImpl(
    private val appContext: Context,
    private val languageDataStore: LanguageDataStore
): LanguageRepository {

    override suspend fun listLanguages(): List<Language> = runBlocking(Dispatchers.IO) {
        val jsonFile = getJsonDataFromAsset(appContext, "languages.json")
        val gson = Gson()
        val data = gson.fromJson(jsonFile, Array<Language>::class.java)
        return@runBlocking data.toList()
    }

    override suspend fun getLanguagePair(): Pair<Language, Language> {
        val sourceLanguageId = languageDataStore.sourceLanguageId.first()
        val destLanguageId = languageDataStore.destLanguageId.first()
        return Pair(
            fetchLanguage(sourceLanguageId),
            fetchLanguage(destLanguageId)
        )
    }


    private suspend fun fetchLanguage(id: String): Language = runBlocking(Dispatchers.IO) {
        val languages = listLanguages()
        return@runBlocking languages.first { it.id == id }
    }
}
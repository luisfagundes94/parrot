package com.luisfagundes.data.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.luisfagundes.data.store.LanguageDataStore
import com.luisfagundes.data.utils.getJsonDataFromAsset
import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.repositories.LanguageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.lang.IllegalStateException

class LanguageRepositoryImpl(
    private val appContext: Context,
    private val languageDataStore: LanguageDataStore
) : LanguageRepository {

    private val languagesMap: MutableMap<String, Language> = mutableMapOf()

    init {
        runBlocking {
            loadLanguages()
        }
    }

    private fun loadLanguages() = runBlocking(Dispatchers.IO) {
        val jsonFile = getJsonDataFromAsset(appContext, "languages.json")
        val gson = Gson()
        val data = gson.fromJson(jsonFile, Array<Language>::class.java)
        data.forEach { language ->
            languagesMap[language.id] = language
        }
    }

    override suspend fun listLanguages(): List<Language> = languagesMap.values.toList()

    override suspend fun getLanguagePair(
        sourceId: String,
        targetId: String
    ): Pair<Language, Language> {
        val sourceLanguageId = sourceId.ifEmpty {
            languageDataStore.sourceLanguageId.first()
        }
        val destLanguageId = targetId.ifEmpty {
            languageDataStore.destLanguageId.first()
        }

        return Pair(
            fetchLanguage(sourceLanguageId),
            fetchLanguage(destLanguageId)
        )
    }

    private fun fetchLanguage(id: String): Language {
        return languagesMap[id] ?: Language(
            id = "d4d5d87e-c0a1-4f35-bb81-5f2709e628aa",
            name = "English",
            nativeName = "English",
            code = "en"
        )
    }
}

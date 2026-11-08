package com.luisfagundes.core.dictionary.data.remote

import com.luisfagundes.core.dictionary.data.model.WordEntryResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface DictionaryApi {

    @GET("api/v2/entries/en/{word}")
    suspend fun getEntries(@Path("word") word: String): List<WordEntryResponse>

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/"
    }
}

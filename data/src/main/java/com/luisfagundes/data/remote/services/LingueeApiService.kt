package com.luisfagundes.data.remote.services

import com.luisfagundes.data.remote.models.WordResponse
import com.luisfagundes.domain.models.Word
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface LingueeApiService {
    @GET("translations")
    suspend fun fetchWordTranslations(
        @QueryMap params: Map<String, String>
    ): List<WordResponse>
}
package com.luisfagundes.data.services

import com.luisfagundes.domain.models.Word
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface LingueeApiService {
    @GET("translations")
    suspend fun fetchWordTranslations(
        @QueryMap params: Map<String, String>
    ): Word
}
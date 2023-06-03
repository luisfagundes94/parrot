package com.luisfagundes.data.remote.services

import com.luisfagundes.data.remote.models.DictionaryLookupBodyResponse
import com.luisfagundes.data.remote.models.TranslateRequestBody
import com.luisfagundes.data.remote.models.TranslationBodyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface MicrosoftTranslateService {
    @POST("translate")
    fun translateText(
        @QueryMap params: Map<String, String>,
        @Body body: TranslateRequestBody,
    ): TranslationBodyResponse

    @GET("dictionary/lookup")
    fun fetchDictionaryLookup(
        @QueryMap params: Map<String, String>,
    ): DictionaryLookupBodyResponse
}

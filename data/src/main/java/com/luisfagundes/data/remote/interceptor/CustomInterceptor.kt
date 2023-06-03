package com.luisfagundes.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor(
    private val apiKey: String,
    private val apiRegion: String,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val httpUrl = original.url
            .newBuilder()
            .build()

        val request = original.newBuilder()
            .url(httpUrl)
            .addHeader("Ocp-Apim-Subscription-Key", apiKey)
            .addHeader("Ocp-Apim-Subscription-Region", apiRegion)
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}

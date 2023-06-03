package com.luisfagundes.data.remote.di

import android.content.Context
import com.luisfagundes.data.BuildConfig
import com.luisfagundes.data.local.datastore.LanguageDataStore
import com.luisfagundes.data.remote.interceptor.CustomInterceptor
import com.luisfagundes.data.remote.services.MicrosoftTranslateService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIME_OUT = 15L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideCountryDataStore(
        @ApplicationContext context: Context,
    ) = LanguageDataStore(context)

    @Provides
    @Singleton
    fun provideHttpRequestInterceptor() = HttpLoggingInterceptor { message ->
        Timber.tag("OkHttp").d(message)
    }.apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun provideCustomInterceptor() = CustomInterceptor(
        apiKey = BuildConfig.API_KEY,
        apiRegion = BuildConfig.API_REGION,
    )

    @Provides
    @Singleton
    fun provideOkHttpClient(
        customInterceptor: CustomInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ) = OkHttpClient.Builder()
        .addInterceptor(customInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MicrosoftTranslateService =
        retrofit.create(MicrosoftTranslateService::class.java)
}

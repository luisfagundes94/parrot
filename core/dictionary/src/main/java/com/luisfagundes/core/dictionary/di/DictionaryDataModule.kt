package com.luisfagundes.core.dictionary.di

import android.content.Context
import androidx.room.Room
import com.luisfagundes.core.dictionary.data.local.DictionaryDao
import com.luisfagundes.core.dictionary.data.local.DictionaryDatabase
import com.luisfagundes.core.dictionary.data.remote.DictionaryApi
import com.luisfagundes.core.dictionary.data.repository.DictionaryRepositoryImpl
import com.luisfagundes.core.dictionary.domain.repository.DictionaryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DictionaryDataBindsModule {

    @Binds
    abstract fun bindDictionaryRepository(impl: DictionaryRepositoryImpl): DictionaryRepository
}

@Module
@InstallIn(SingletonComponent::class)
internal object DictionaryDataProvidesModule {

    @Provides
    @Singleton
    fun provideDictionaryJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideDictionaryDatabase(@ApplicationContext context: Context): DictionaryDatabase =
        Room.databaseBuilder(context, DictionaryDatabase::class.java, DictionaryDatabase.NAME).build()

    @Provides
    fun provideDictionaryDao(database: DictionaryDatabase): DictionaryDao = database.dictionaryDao()

    @Provides
    @Singleton
    fun provideDictionaryApi(json: Json): DictionaryApi = Retrofit.Builder()
        .baseUrl(DictionaryApi.BASE_URL)
        .client(OkHttpClient())
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(DictionaryApi::class.java)
}

package com.luisfagundes.feature.search.impl.di

import android.content.Context
import androidx.room.Room
import com.luisfagundes.feature.search.impl.data.autocomplete.WordSuggestionRepositoryImpl
import com.luisfagundes.feature.search.impl.data.local.DictionaryDao
import com.luisfagundes.feature.search.impl.data.local.SearchDatabase
import com.luisfagundes.feature.search.impl.data.remote.DictionaryApi
import com.luisfagundes.feature.search.impl.data.repository.DictionaryRepositoryImpl
import com.luisfagundes.feature.search.impl.domain.repository.DictionaryRepository
import com.luisfagundes.feature.search.impl.domain.repository.WordSuggestionRepository
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
internal abstract class SearchDataBindsModule {

    @Binds
    abstract fun bindDictionaryRepository(impl: DictionaryRepositoryImpl): DictionaryRepository

    @Binds
    abstract fun bindWordSuggestionRepository(
        impl: WordSuggestionRepositoryImpl,
    ): WordSuggestionRepository
}

@Module
@InstallIn(SingletonComponent::class)
internal object SearchDataProvidesModule {

    @Provides
    @Singleton
    fun provideSearchJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideSearchDatabase(@ApplicationContext context: Context): SearchDatabase =
        Room.databaseBuilder(context, SearchDatabase::class.java, SearchDatabase.NAME).build()

    @Provides
    fun provideDictionaryDao(database: SearchDatabase): DictionaryDao = database.dictionaryDao()

    @Provides
    @Singleton
    fun provideDictionaryApi(json: Json): DictionaryApi = Retrofit.Builder()
        .baseUrl(DictionaryApi.BASE_URL)
        .client(OkHttpClient())
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(DictionaryApi::class.java)
}

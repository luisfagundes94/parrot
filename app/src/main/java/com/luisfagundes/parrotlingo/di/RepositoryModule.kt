package com.luisfagundes.parrotlingo.di

import android.content.Context
import com.luisfagundes.data.repositories.LanguageRepositoryImpl
import com.luisfagundes.data.repositories.WordRepositoryImpl
import com.luisfagundes.data.services.LingueeApiService
import com.luisfagundes.data.store.LanguageDataStore
import com.luisfagundes.domain.repositories.LanguageRepository
import com.luisfagundes.domain.repositories.WordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideCountryRepository(
        @ApplicationContext appContext: Context,
        dataStore: LanguageDataStore
    ): LanguageRepository = LanguageRepositoryImpl(
        appContext = appContext,
        languageDataStore = dataStore
    )

    @Provides
    fun provideWordRepository(
        apiService: LingueeApiService
    ): WordRepository = WordRepositoryImpl(
        lingueeApiService = apiService
    )

}
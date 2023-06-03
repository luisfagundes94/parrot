package com.luisfagundes.parrotlingo.di

import android.content.Context
import com.luisfagundes.data.local.database.ParrotDatabase
import com.luisfagundes.data.local.datastore.LanguageDataStore
import com.luisfagundes.data.remote.services.MicrosoftTranslateService
import com.luisfagundes.data.repositories.LanguageRepositoryImpl
import com.luisfagundes.data.repositories.WordRepositoryImpl
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
        dataStore: LanguageDataStore,
    ): LanguageRepository = LanguageRepositoryImpl(
        appContext = appContext,
        languageDataStore = dataStore,
    )

    @Provides
    fun provideWordRepository(
        apiService: MicrosoftTranslateService,
        parrotDatabase: ParrotDatabase,
    ): WordRepository = WordRepositoryImpl(
        apiService = apiService,
        database = parrotDatabase,
    )
}

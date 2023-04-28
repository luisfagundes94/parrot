package com.luisfagundes.parrotlingo.di

import com.luisfagundes.data.repositories.WordRepositoryImpl
import com.luisfagundes.data.services.LingueeApiService
import com.luisfagundes.domain.repositories.WordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideWordRepository(
        apiService: LingueeApiService
    ): WordRepository = WordRepositoryImpl(apiService)

}
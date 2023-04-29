package com.luisfagundes.data.di

import com.luisfagundes.domain.repositories.WordRepository
import com.luisfagundes.domain.usecases.GetLanguageName
import com.luisfagundes.domain.usecases.GetWordTranslations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetWordTranslations(repository: WordRepository) =
        GetWordTranslations(repository)

    @Provides
    fun provideGetCountryCodeFromFiles() =
        GetLanguageName()
}
package com.luisfagundes.data.di

import com.luisfagundes.domain.repositories.LanguageRepository
import com.luisfagundes.domain.repositories.WordRepository
import com.luisfagundes.domain.usecases.GetLanguagePair
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.domain.usecases.ListCountries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideCountryPair(repository: LanguageRepository) =
        GetLanguagePair(repository)

    @Provides
    fun provideGetWordTranslations(repository: WordRepository) =
        GetWordTranslations(repository)


    @Provides
    fun provideCountryList(repository: LanguageRepository) =
        ListCountries(repository)
}
package com.luisfagundes.data.di

import com.luisfagundes.domain.repositories.CountryRepository
import com.luisfagundes.domain.repositories.WordRepository
import com.luisfagundes.domain.usecases.GetLanguageName
import com.luisfagundes.domain.usecases.GetCountryPair
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
    fun provideCountryPair(repository: CountryRepository) =
        GetCountryPair(repository)

    @Provides
    fun provideGetWordTranslations(repository: WordRepository) =
        GetWordTranslations(repository)

    @Provides
    fun provideGetCountryCodeFromFiles() =
        GetLanguageName()

    @Provides
    fun provideCountryList(repository: CountryRepository) =
        ListCountries(repository)
}
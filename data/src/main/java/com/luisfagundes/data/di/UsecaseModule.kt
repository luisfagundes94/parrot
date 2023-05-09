package com.luisfagundes.data.di

import com.luisfagundes.domain.repositories.LanguageRepository
import com.luisfagundes.domain.repositories.WordRepository
import com.luisfagundes.domain.usecases.GetLanguagePair
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.domain.usecases.ListLanguages
import com.luisfagundes.domain.usecases.UpdateLanguage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideUpdateLanguage(repository: LanguageRepository) =
        UpdateLanguage(repository)

    @Provides
    fun provideLanguagePair(repository: LanguageRepository) =
        GetLanguagePair(repository)

    @Provides
    fun provideGetWordTranslations(repository: WordRepository) =
        GetWordTranslations(repository)


    @Provides
    fun provideLanguageList(repository: LanguageRepository) =
        ListLanguages(repository)
}
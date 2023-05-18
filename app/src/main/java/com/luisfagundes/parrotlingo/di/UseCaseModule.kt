package com.luisfagundes.parrotlingo.di

import android.content.Context
import com.luisfagundes.domain.managers.PushNotificationManager
import com.luisfagundes.domain.repositories.LanguageRepository
import com.luisfagundes.domain.repositories.WordRepository
import com.luisfagundes.domain.usecases.CancelNotification
import com.luisfagundes.domain.usecases.DeleteWord
import com.luisfagundes.domain.usecases.GetAllSavedWords
import com.luisfagundes.domain.usecases.GetLanguagePair
import com.luisfagundes.domain.usecases.GetWordTranslations
import com.luisfagundes.domain.usecases.ListLanguages
import com.luisfagundes.domain.usecases.SaveWord
import com.luisfagundes.domain.usecases.UpdateLanguage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideCancelNotification(
        @ApplicationContext context: Context,
        pushNotificationManager: PushNotificationManager,
    ) = CancelNotification(context, pushNotificationManager)

    @Provides
    fun provideDeleteWord(repository: WordRepository) =
        DeleteWord(repository)

    @Provides
    fun provideGetAllSavedWords(repository: WordRepository) =
        GetAllSavedWords(repository)

    @Provides
    fun provideSaveWord(repository: WordRepository) =
        SaveWord(repository)

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

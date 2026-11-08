package com.luisfagundes.feature.word.impl.di

import com.luisfagundes.core.navigation.EntryProviderInstaller
import com.luisfagundes.feature.word.api.navigation.WordDetailNavKey
import com.luisfagundes.feature.word.impl.presentation.screen.WordDetailScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal object WordNavModule {
    @Provides
    @IntoSet
    fun installer(): EntryProviderInstaller = { navigator ->
        entry<WordDetailNavKey> { key ->
            WordDetailScreen(
                word = key.word,
                onNavigateBack = navigator::goBack
            )
        }
    }
}

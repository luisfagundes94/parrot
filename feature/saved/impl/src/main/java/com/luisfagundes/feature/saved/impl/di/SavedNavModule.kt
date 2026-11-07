package com.luisfagundes.feature.saved.impl.di

import com.luisfagundes.core.navigation.EntryProviderInstaller
import com.luisfagundes.feature.saved.api.navigation.SavedNavKey
import com.luisfagundes.feature.saved.impl.presentation.screen.SavedScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal object SavedNavModule {
    @Provides
    @IntoSet
    fun installer(): EntryProviderInstaller = {
        entry<SavedNavKey> { SavedScreen() }
    }
}
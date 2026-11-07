package com.luisfagundes.feature.search.impl.di

import com.luisfagundes.core.navigation.EntryProviderInstaller
import com.luisfagundes.feature.search.api.navigation.SearchNavKey
import com.luisfagundes.feature.search.impl.presentation.screen.SearchScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal object SearchNavModule {
    @Provides
    @IntoSet
    fun installer(): EntryProviderInstaller = {
        entry<SearchNavKey> { SearchScreen() }
    }
}
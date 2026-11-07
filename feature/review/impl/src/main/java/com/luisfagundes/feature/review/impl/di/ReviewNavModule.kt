package com.luisfagundes.feature.review.impl.di

import com.luisfagundes.core.navigation.EntryProviderInstaller
import com.luisfagundes.feature.review.api.navigation.ReviewNavKey
import com.luisfagundes.feature.review.impl.presentation.screen.ReviewScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal object ReviewNavModule {
    @Provides
    @IntoSet
    fun installer(): EntryProviderInstaller = {
        entry<ReviewNavKey> { ReviewScreen() }
    }
}
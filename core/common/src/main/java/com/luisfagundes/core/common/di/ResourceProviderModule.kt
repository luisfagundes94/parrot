package com.luisfagundes.core.common.di

import android.content.Context
import com.luisfagundes.core.common.tools.ResourceProvider
import com.luisfagundes.core.common.tools.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ResourceProviderModule {
    @Singleton
    @Provides
    fun provideResourceProviderImpl(
        @ApplicationContext context: Context
    ): ResourceProvider = ResourceProviderImpl(context)
}
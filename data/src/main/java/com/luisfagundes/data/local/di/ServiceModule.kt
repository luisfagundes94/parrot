package com.luisfagundes.data.local.di

import android.content.Context
import com.luisfagundes.data.local.services.AlarmSchedulerImpl
import com.luisfagundes.domain.services.AlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideNotificationScheduler(
        @ApplicationContext context: Context,
    ): AlarmScheduler = AlarmSchedulerImpl(
        context = context,
    )
}

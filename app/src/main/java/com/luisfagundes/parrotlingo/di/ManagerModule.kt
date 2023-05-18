package com.luisfagundes.parrotlingo.di

import com.luisfagundes.data.local.services.PushNotificationManagerImpl
import com.luisfagundes.domain.managers.PushNotificationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagerModule {
    @Singleton
    @Provides
    fun providePushNotificationManager(): PushNotificationManager =
        PushNotificationManagerImpl()
}

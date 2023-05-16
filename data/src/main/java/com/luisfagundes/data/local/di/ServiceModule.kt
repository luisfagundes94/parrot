package com.luisfagundes.data.local.di

import android.app.AlarmManager
import android.content.Context
import com.luisfagundes.data.local.services.AlarmSchedulerImpl
import com.luisfagundes.data.local.services.NotificationService
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
    fun provideNotificationService() = NotificationService()

    @Singleton
    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @Singleton
    @Provides
    fun provideNotificationScheduler(
        @ApplicationContext context: Context,
        alarmManager: AlarmManager,
    ): AlarmScheduler = AlarmSchedulerImpl(
        context = context,
        alarmManager = alarmManager,
    )
}

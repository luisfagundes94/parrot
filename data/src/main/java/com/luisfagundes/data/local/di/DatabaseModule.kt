package com.luisfagundes.data.local.di

import android.content.Context
import androidx.room.Room
import com.luisfagundes.data.local.database.ParrotDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ) = Room.databaseBuilder(
        applicationContext,
        ParrotDatabase::class.java,
        DATABASE_NAME
    ).build()

    private const val DATABASE_NAME = "parrot.db"
}
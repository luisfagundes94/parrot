package com.luisfagundes.data.di

import com.luisfagundes.data.repositories.WordRepositoryImpl
import com.luisfagundes.domain.repositories.WordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindWordRepository(repository: WordRepositoryImpl): WordRepository
}
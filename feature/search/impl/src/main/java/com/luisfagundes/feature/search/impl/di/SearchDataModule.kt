package com.luisfagundes.feature.search.impl.di

import com.luisfagundes.feature.search.impl.data.autocomplete.WordSuggestionRepositoryImpl
import com.luisfagundes.feature.search.impl.domain.repository.WordSuggestionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SearchDataBindsModule {

    @Binds
    abstract fun bindWordSuggestionRepository(
        impl: WordSuggestionRepositoryImpl,
    ): WordSuggestionRepository
}

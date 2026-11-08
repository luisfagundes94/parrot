package com.luisfagundes.feature.word.impl.di

import com.luisfagundes.feature.word.impl.data.speech.TextToSpeechWordSpeaker
import com.luisfagundes.feature.word.impl.domain.speech.WordSpeaker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class WordSpeechModule {

    // Unscoped on purpose: every ViewModel owns (and releases) its own engine.
    @Binds
    abstract fun bindWordSpeaker(impl: TextToSpeechWordSpeaker): WordSpeaker
}

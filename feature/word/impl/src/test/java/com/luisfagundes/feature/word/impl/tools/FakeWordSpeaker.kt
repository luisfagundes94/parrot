package com.luisfagundes.feature.word.impl.tools

import com.luisfagundes.feature.word.impl.domain.speech.WordSpeaker
import kotlinx.coroutines.CompletableDeferred

internal class FakeWordSpeaker : WordSpeaker {
    val spoken = mutableListOf<String>()
    private var pending: CompletableDeferred<Result<Unit>>? = null

    override suspend fun speak(word: String): Result<Unit> {
        spoken += word
        return CompletableDeferred<Result<Unit>>().also { pending = it }.await()
    }

    override fun release() = Unit

    fun finish(result: Result<Unit>) {
        pending?.complete(result)
    }
}

package com.luisfagundes.feature.word.impl.domain.speech

internal interface WordSpeaker {
    /**
     * Pronounces [word] and suspends until it has been spoken. Cancelling the calling coroutine
     * stops the speech. Fails when no speech engine or English voice is available.
     * Must be called from the main thread.
     */
    suspend fun speak(word: String): Result<Unit>

    /** Frees the speech engine. Call once the owner (the ViewModel) is cleared. */
    fun release()
}

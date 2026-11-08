package com.luisfagundes.feature.word.impl.data.speech

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import com.luisfagundes.core.common.tools.safeRunCatching
import com.luisfagundes.feature.word.impl.domain.speech.WordSpeaker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

/** Not a singleton: each owner gets its own engine and must call [release]. */
internal class TextToSpeechWordSpeaker @Inject constructor(
    @ApplicationContext private val context: Context
) : WordSpeaker {

    private var engine: TextToSpeech? = null

    override suspend fun speak(word: String): Result<Unit> = safeRunCatching {
        val tts = engine ?: initEngine().also { engine = it }
        suspendCancellableCoroutine<Result<Unit>> { continuation ->
            fun finish(result: Result<Unit>) {
                if (continuation.isActive) continuation.resume(result)
            }

            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) = Unit
                override fun onDone(utteranceId: String?) = finish(Result.success(Unit))
                override fun onStop(utteranceId: String?, interrupted: Boolean) =
                    finish(Result.success(Unit))

                @Deprecated("Superseded by onError(utteranceId, errorCode)")
                override fun onError(utteranceId: String?) =
                    finish(Result.failure(IllegalStateException("Speech failed")))

                override fun onError(utteranceId: String?, errorCode: Int) =
                    finish(Result.failure(IllegalStateException("Speech failed ($errorCode)")))
            })
            continuation.invokeOnCancellation { tts.stop() }

            val queued = tts.speak(word, TextToSpeech.QUEUE_FLUSH, null, UUID.randomUUID().toString())
            if (queued == TextToSpeech.ERROR) {
                finish(Result.failure(IllegalStateException("Speech could not be queued")))
            }
        }.getOrThrow()
    }

    private suspend fun initEngine(): TextToSpeech =
        suspendCancellableCoroutine { continuation ->
            var created: TextToSpeech? = null
            created = TextToSpeech(context) { status ->
                val tts = created
                when {
                    tts == null || status != TextToSpeech.SUCCESS -> {
                        tts?.shutdown()
                        continuation.resumeWithException(
                            IllegalStateException("Text to speech is unavailable")
                        )
                    }
                    !tts.supportsEnglish() -> {
                        tts.shutdown()
                        continuation.resumeWithException(
                            IllegalStateException("No English voice is installed")
                        )
                    }
                    else -> continuation.resume(tts)
                }
            }
            continuation.invokeOnCancellation { created?.shutdown() }
        }

    private fun TextToSpeech.supportsEnglish(): Boolean {
        val result = setLanguage(Locale.US)
        return result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
    }

    override fun release() {
        engine?.stop()
        engine?.shutdown()
        engine = null
    }
}

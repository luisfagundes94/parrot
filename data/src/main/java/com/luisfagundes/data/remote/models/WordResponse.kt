package com.luisfagundes.data.remote.models

import com.google.gson.annotations.SerializedName

data class WordResponse(
    @SerializedName("audio_links") val audioLinks: List<AudioLinkResponse>,
    val featured: Boolean,
    @SerializedName("grammar_info") val grammarInfo: Any? = null,
    val pos: String,
    val text: String,
    val translations: List<TranslationResponse>,
)

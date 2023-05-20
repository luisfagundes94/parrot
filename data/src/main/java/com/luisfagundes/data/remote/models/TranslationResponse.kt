package com.luisfagundes.data.remote.models

import com.google.gson.annotations.SerializedName

data class TranslationResponse(
    @SerializedName("audio_link") val audioLinks: List<AudioLinkResponse>,
    val examples: List<ExampleResponse>,
    val featured: Boolean,
    val pos: String,
    val text: String,
    @SerializedName("usage_frequency") val usageFrequency: Any?,
)

package com.luisfagundes.data.models

data class TranslationResponse(
    val audio_links: List<AudioLinkResponse>,
    val examples: List<ExampleResponse>,
    val featured: Boolean,
    val pos: String,
    val text: String,
    val usage_frequency: Any?
)

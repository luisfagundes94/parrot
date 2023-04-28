package com.luisfagundes.data.models

import com.luisfagundes.domain.models.AudioLink
import com.luisfagundes.domain.models.Translation

data class WordResponse(
    val audio_links: List<AudioLink>,
    val featured: Boolean,
    val grammar_info: Any?,
    val pos: String,
    val text: String,
    val translations: List<Translation>
)

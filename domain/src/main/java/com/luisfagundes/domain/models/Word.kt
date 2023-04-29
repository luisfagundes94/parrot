package com.luisfagundes.domain.models

data class Word(
    val audioLinks: List<AudioLink>,
    val featured: Boolean,
    val grammarInfo: Any? = null,
    val type: String,
    val text: String,
    val translations: List<Translation>
)

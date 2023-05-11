package com.luisfagundes.domain.models

data class Word(
    val id: String,
    val audioLinks: List<AudioLink>,
    val featured: Boolean,
    val type: String,
    val text: String,
    val translations: List<Translation>
)

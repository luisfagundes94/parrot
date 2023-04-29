package com.luisfagundes.domain.models

data class Translation(
    val audioLinks: List<AudioLink>,
    val examples: List<Example>,
    val featured: Boolean,
    val wordType: String,
    val text: String,
    val usageFrequency: Any?
)

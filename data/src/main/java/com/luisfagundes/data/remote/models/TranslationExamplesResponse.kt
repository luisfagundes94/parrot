package com.luisfagundes.data.remote.models

data class TranslationExamplesResponse(
    val examples: List<ExampleResponse>,
    val normalizedSource: String,
    val normalizedTarget: String
)
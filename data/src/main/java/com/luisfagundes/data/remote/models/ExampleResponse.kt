package com.luisfagundes.data.remote.models

data class ExampleResponse(
    val sourcePrefix: String,
    val sourceSuffix: String,
    val sourceTerm: String,
    val targetPrefix: String,
    val targetSuffix: String,
    val targetTerm: String
)
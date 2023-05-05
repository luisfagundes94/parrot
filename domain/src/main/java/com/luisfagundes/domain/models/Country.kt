package com.luisfagundes.domain.models

data class Country(
    val id: String = "",
    val name: String,
    val flagUrl: String,
    val languages: List<String>,
    val code: String
)

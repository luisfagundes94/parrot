package com.luisfagundes.feature.search.impl.domain.model

internal class WordNotFoundException(word: String) : Exception("No definitions found for \"$word\"")

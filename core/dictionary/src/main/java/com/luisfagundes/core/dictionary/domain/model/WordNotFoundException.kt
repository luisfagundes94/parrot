package com.luisfagundes.core.dictionary.domain.model

class WordNotFoundException(word: String) : Exception("No definitions found for \"$word\"")

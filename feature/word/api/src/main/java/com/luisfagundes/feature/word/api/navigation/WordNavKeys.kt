package com.luisfagundes.feature.word.api.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class WordDetailNavKey(val word: String) : NavKey

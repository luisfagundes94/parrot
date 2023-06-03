package com.luisfagundes.translation.presentation

import com.luisfagundes.domain.models.Language
import com.luisfagundes.domain.models.Word

data class TranslationViewState(
    val word: Word? = null,
    val languagePair: Pair<Language, Language>? = null
)
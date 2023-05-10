package com.luisfagundes.domain.modelFactory

import com.luisfagundes.domain.models.Language

object LanguageFactory {
    val languages = listOf(
        Language(
            name = "English",
            nativeName = "English",
            code = "en"
        ),
        Language(
            name = "Portuguese",
            nativeName = "Português",
            code = "pt"
        ),
    )
}
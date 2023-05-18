package com.luisfagundes.domain.modelFactory

import com.luisfagundes.domain.models.Translation
import com.luisfagundes.domain.models.Word

object WordFactory {
    val words = listOf(
        Word(
            id = System.currentTimeMillis().toInt(),
            audioLinks = emptyList(),
            featured = true,
            type = "noun",
            text = "overload",
            translations = listOf(
                Translation(
                    id = 1.toString(),
                    audioLinks = emptyList(),
                    examples = emptyList(),
                    wordType = "noun",
                    text = "sobrecarga",
                    usageFrequency = null,
                    featured = true,
                ),
            ),
        ),
    )
}

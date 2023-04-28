package com.luisfagundes.data.mapper

import com.luisfagundes.data.models.WordResponse
import com.luisfagundes.domain.models.Word

object WordMapper {
    fun List<WordResponse>.toDomain(): List<Word> {
        return this.map { it.toDomain() }
    }

    private fun WordResponse.toDomain(): Word {
        return Word(
            audioLinks = this.audio_links,
            featured = this.featured,
            grammarInfo = this.grammar_info,
            type = this.pos,
            text = this.text,
            translations = this.translations
        )
    }
}
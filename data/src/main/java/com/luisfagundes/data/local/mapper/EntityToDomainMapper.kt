package com.luisfagundes.data.local.mapper

import com.luisfagundes.data.local.models.AudioLinkEntity
import com.luisfagundes.data.local.models.ExampleEntity
import com.luisfagundes.data.local.models.TranslationEntity
import com.luisfagundes.data.local.models.WordEntity
import com.luisfagundes.domain.models.AudioLink
import com.luisfagundes.domain.models.Example
import com.luisfagundes.domain.models.Translation
import com.luisfagundes.domain.models.Word

object EntityToDomainMapper {
  fun WordEntity.toDomain() = Word(
    id = this.uid,
    text = this.text,
    translations = this.translations.map { it.toDomain() },
    type = this.type,
    featured = this.featured,
    audioLinks = audioLinks.map { it.toDomain() },
  )

  private fun AudioLinkEntity.toDomain() = AudioLink(
    id = this.uid,
    language = this.language,
    url = this.url,
  )

  private fun TranslationEntity.toDomain() = Translation(
    id = this.uid,
    audioLinks = this.audioLinks.map { it.toDomain() },
    examples = this.examples.map { it.toDomain() },
    featured = this.featured,
    wordType = this.wordType,
    text = this.text,
    usageFrequency = this.usageFrequency,
  )

  private fun ExampleEntity.toDomain() = Example(
    id = this.uid,
    destinationLanguage = this.destinationLanguage,
    sourceLanguage = this.sourceLanguage,
  )
}

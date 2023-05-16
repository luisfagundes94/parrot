package com.luisfagundes.data.local.mapper

import com.luisfagundes.data.local.models.AudioLinkEntity
import com.luisfagundes.data.local.models.ExampleEntity
import com.luisfagundes.data.local.models.TranslationEntity
import com.luisfagundes.data.local.models.WordEntity
import com.luisfagundes.domain.models.AudioLink
import com.luisfagundes.domain.models.Example
import com.luisfagundes.domain.models.Translation
import com.luisfagundes.domain.models.Word

object DomainToEntityMapper {
  fun Word.toEntity() = WordEntity(
    uid = this.id,
    audioLinks = this.audioLinks.map { it.toEntity() },
    featured = this.featured,
    type = this.type,
    text = this.text,
    translations = this.translations.map { it.toEntity() },
  )

  private fun AudioLink.toEntity() = AudioLinkEntity(
    uid = this.id,
    language = this.language,
    url = this.url,
  )

  private fun Translation.toEntity() = TranslationEntity(
    uid = this.id,
    audioLinks = this.audioLinks.map { it.toEntity() },
    examples = this.examples.map { it.toEntity() },
    featured = this.featured,
    wordType = this.wordType,
    text = this.text,
    usageFrequency = this.usageFrequency,
  )

  private fun Example.toEntity() = ExampleEntity(
    uid = this.id,
    destinationLanguage = this.destinationLanguage,
    sourceLanguage = this.sourceLanguage,
  )
}

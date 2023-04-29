package com.luisfagundes.data.mapper

import com.luisfagundes.data.models.AudioLinkResponse
import com.luisfagundes.data.models.ExampleResponse
import com.luisfagundes.data.models.TranslationResponse
import com.luisfagundes.data.models.WordResponse
import com.luisfagundes.domain.models.AudioLink
import com.luisfagundes.domain.models.Example
import com.luisfagundes.domain.models.Translation
import com.luisfagundes.domain.models.Word

object WordMapper {
    @JvmName("toDomainWordResponse")
    fun List<WordResponse>?.toDomain() =
        this?.map { it.toDomain() } ?: emptyList()

    fun WordResponse.toDomain() = Word(
        audioLinks = this.audio_links.toDomain(),
        featured = this.featured,
        grammarInfo = this.grammar_info,
        type = this.pos,
        text = this.text,
        translations = this.translations.toDomain()
    )

    @JvmName("toDomainAudioLinkResponse")
    fun List<AudioLinkResponse>?.toDomain() =
        this?.map { it.toDomain() } ?: emptyList()

    fun AudioLinkResponse.toDomain() = AudioLink(
        language = this.lang,
        url = this.url
    )

    @JvmName("toDomainTranslationResponse")
    fun List<TranslationResponse>?.toDomain() =
        this?.map { it.toDomain() } ?: emptyList()

    fun TranslationResponse.toDomain() = Translation(
        audioLinks = this.audio_links.toDomain(),
        examples = this.examples.toDomain(),
        featured = this.featured,
        wordType = this.pos,
        text = this.text,
        usageFrequency = this.usage_frequency
    )

    @JvmName("toDomainExampleResponse")
    fun List<ExampleResponse>?.toDomain() =
        this?.map { it.toDomain() } ?: emptyList()

    fun ExampleResponse.toDomain() = Example(
        destinationLanguage = this.dst,
        sourceLanguage = this.src,
    )

}
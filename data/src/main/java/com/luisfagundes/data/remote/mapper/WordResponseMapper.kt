package com.luisfagundes.data.remote.mapper

import com.luisfagundes.data.remote.models.AudioLinkResponse
import com.luisfagundes.data.remote.models.ExampleResponse
import com.luisfagundes.data.remote.models.TranslationResponse
import com.luisfagundes.data.remote.models.WordResponse
import com.luisfagundes.domain.models.AudioLink
import com.luisfagundes.domain.models.Example
import com.luisfagundes.domain.models.Translation
import com.luisfagundes.domain.models.Word
import java.util.UUID

object WordResponseMapper {
    @JvmName("toDomainWordResponse")
    fun List<WordResponse>?.toDomain() =
        this?.map { it.toDomain() } ?: emptyList()

    fun WordResponse.toDomain() = Word(
        id = UUID.randomUUID().toString(),
        audioLinks = this.audio_links.toDomain(),
        featured = this.featured,
        type = this.pos,
        text = this.text,
        translations = this.translations.toDomain(),
    )

    @JvmName("toDomainAudioLinkResponse")
    fun List<AudioLinkResponse>?.toDomain() =
        this?.map { it.toDomain() } ?: emptyList()

    fun AudioLinkResponse.toDomain() = AudioLink(
        id = UUID.randomUUID().toString(),
        language = this.lang,
        url = this.url,
    )

    @JvmName("toDomainTranslationResponse")
    fun List<TranslationResponse>?.toDomain() =
        this?.map { it.toDomain() } ?: emptyList()

    fun TranslationResponse.toDomain() = Translation(
        id = UUID.randomUUID().toString(),
        audioLinks = this.audio_links.toDomain(),
        examples = this.examples.toDomain(),
        featured = this.featured,
        wordType = this.pos,
        text = this.text,
        usageFrequency = this.usage_frequency,
    )

    @JvmName("toDomainExampleResponse")
    fun List<ExampleResponse>?.toDomain() =
        this?.map { it.toDomain() } ?: emptyList()

    fun ExampleResponse.toDomain() = Example(
        id = UUID.randomUUID().toString(),
        destinationLanguage = this.dst,
        sourceLanguage = this.src,
    )
}

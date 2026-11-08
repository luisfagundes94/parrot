package com.luisfagundes.core.dictionary.data.model

import com.luisfagundes.core.dictionary.domain.model.Definition
import com.luisfagundes.core.dictionary.domain.model.Meaning
import com.luisfagundes.core.dictionary.domain.model.WordEntry
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class WordEntryResponse(
    @SerialName("word") val word: String,
    @SerialName("phonetic") val phonetic: String? = null,
    @SerialName("phonetics") val phonetics: List<PhoneticResponse> = emptyList(),
    @SerialName("meanings") val meanings: List<MeaningResponse> = emptyList(),
)

@Serializable
internal data class PhoneticResponse(
    @SerialName("text") val text: String? = null,
    @SerialName("audio") val audio: String? = null,
)

@Serializable
internal data class MeaningResponse(
    @SerialName("partOfSpeech") val partOfSpeech: String = "",
    @SerialName("definitions") val definitions: List<DefinitionResponse> = emptyList(),
    @SerialName("synonyms") val synonyms: List<String> = emptyList(),
    @SerialName("antonyms") val antonyms: List<String> = emptyList(),
)

@Serializable
internal data class DefinitionResponse(
    @SerialName("definition") val definition: String,
    @SerialName("example") val example: String? = null,
)

internal fun WordEntryResponse.toDomain() = WordEntry(
    word = word,
    phonetic = phonetic?.takeIf { it.isNotBlank() }
        ?: phonetics.firstNotNullOfOrNull { it.text?.takeIf(String::isNotBlank) },
    audioUrl = phonetics.firstNotNullOfOrNull { it.audio?.takeIf(String::isNotBlank) },
    meanings = meanings.map { it.toDomain() },
)

private fun MeaningResponse.toDomain() = Meaning(
    partOfSpeech = partOfSpeech,
    definitions = definitions.map { Definition(text = it.definition, example = it.example) },
    synonyms = synonyms,
    antonyms = antonyms,
)

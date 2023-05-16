package com.luisfagundes.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "translation")
data class TranslationEntity(
  @PrimaryKey val uid: String,
  @ColumnInfo("audio_links") val audioLinks: List<AudioLinkEntity>,
  @ColumnInfo("examples") val examples: List<ExampleEntity>,
  @ColumnInfo("featured") val featured: Boolean,
  @ColumnInfo("word_type") val wordType: String,
  @ColumnInfo("text") val text: String,
  @ColumnInfo("usage_frequency") val usageFrequency: Any?,
)

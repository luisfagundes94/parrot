package com.luisfagundes.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
data class WordEntity(
  @PrimaryKey val uid: String,
  @ColumnInfo("audio_links") val audioLinks: List<AudioLinkEntity>,
  @ColumnInfo("featured") val featured: Boolean,
  @ColumnInfo("type") val type: String,
  @ColumnInfo("text") val text: String,
  @ColumnInfo("translations") val translations: List<TranslationEntity>,
)

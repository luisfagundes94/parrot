package com.luisfagundes.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audio_link")
data class AudioLinkEntity(
    @PrimaryKey val uid: String,
    @ColumnInfo("language") val language: String,
    @ColumnInfo("url") val url: String,
)

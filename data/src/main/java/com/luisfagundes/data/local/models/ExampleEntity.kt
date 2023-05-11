package com.luisfagundes.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "example")
data class ExampleEntity(
    @PrimaryKey val uid: String,
    @ColumnInfo("destinationLanguage") val destinationLanguage: String,
    @ColumnInfo("sourceLanguage") val sourceLanguage: String
)

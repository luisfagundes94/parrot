package com.luisfagundes.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.luisfagundes.data.local.database.typeConverters.AudioLinkEntityTypeConverter
import com.luisfagundes.data.local.database.typeConverters.ExampleEntityTypeConverter
import com.luisfagundes.data.local.database.typeConverters.TranslationEntityTypeConverter
import com.luisfagundes.data.local.models.AudioLinkEntity
import com.luisfagundes.data.local.models.ExampleEntity
import com.luisfagundes.data.local.models.WordEntity

@Database(
    entities = [
        AudioLinkEntity::class,
        ExampleEntity::class,
        WordEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(
    AudioLinkEntityTypeConverter::class,
    ExampleEntityTypeConverter::class,
    TranslationEntityTypeConverter::class,
)
abstract class ParrotDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}

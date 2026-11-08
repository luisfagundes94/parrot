package com.luisfagundes.core.dictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CachedWordEntity::class], version = 1, exportSchema = false)
internal abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao

    companion object {
        const val NAME = "dictionary.db"
    }
}

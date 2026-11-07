package com.luisfagundes.feature.search.impl.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CachedWordEntity::class], version = 1, exportSchema = false)
internal abstract class SearchDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao

    companion object {
        const val NAME = "search.db"
    }
}

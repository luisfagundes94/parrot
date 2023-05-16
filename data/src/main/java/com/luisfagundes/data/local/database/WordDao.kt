package com.luisfagundes.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luisfagundes.data.local.models.WordEntity

@Dao
interface WordDao {

    @Query("SELECT * FROM word")
    fun getAll(): List<WordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: WordEntity): Long

    @Delete
    fun delete(word: WordEntity): Int
}

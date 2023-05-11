package com.luisfagundes.data.local.database.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.luisfagundes.data.local.models.AudioLinkEntity
import com.luisfagundes.data.local.models.ExampleEntity

class ExampleEntityTypeConverter {
    @TypeConverter
    fun fromExampleList(examples: List<ExampleEntity>?): String {
        val gson = Gson()
        return gson.toJson(examples)
    }

    @TypeConverter
    fun toExampleList(examplesInString: String?): List<ExampleEntity>? {
        val gson = Gson()
        val type = object : TypeToken<List<AudioLinkEntity>>() {}.type
        return gson.fromJson(examplesInString, type)
    }
}
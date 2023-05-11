package com.luisfagundes.data.local.database.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.luisfagundes.data.local.models.TranslationEntity

class TranslationEntityTypeConverter {
    @TypeConverter
    fun fromTranslationList(translations: List<TranslationEntity>?): String {
        val gson = Gson()
        return gson.toJson(translations)
    }

    @TypeConverter
    fun toTranslationList(translationsInString: String?): List<TranslationEntity>? {
        val gson = Gson()
        val type = object : TypeToken<List<TranslationEntity>>() {}.type
        return gson.fromJson(translationsInString, type)
    }
}
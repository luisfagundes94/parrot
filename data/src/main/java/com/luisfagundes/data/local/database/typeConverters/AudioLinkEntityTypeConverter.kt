package com.luisfagundes.data.local.database.typeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.luisfagundes.data.local.models.AudioLinkEntity

class AudioLinkEntityTypeConverter {

    @TypeConverter
    fun fromAudioLinkEntityList(audioLinks: List<AudioLinkEntity>?): String {
        val gson = Gson()
        return gson.toJson(audioLinks)
    }

    @TypeConverter
    fun toAudioLinkEntityList(audioLinksString: String?): List<AudioLinkEntity>? {
        val gson = Gson()
        val type = object : TypeToken<List<AudioLinkEntity>>() {}.type
        return gson.fromJson(audioLinksString, type)
    }
}
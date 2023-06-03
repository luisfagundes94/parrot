package com.luisfagundes.data.remote.models

import com.google.gson.annotations.SerializedName

data class TranslationResponse(
    @SerializedName("text") val text: String,
    @SerializedName("to") val to: String,
)

package com.luisfagundes.data.remote.models

import com.google.gson.annotations.SerializedName

data class TranslationBodyResponse(
    @SerializedName("translations") val translations: List<TranslationResponse>
)

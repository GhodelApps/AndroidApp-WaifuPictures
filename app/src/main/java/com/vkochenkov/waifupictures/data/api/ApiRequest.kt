package com.vkochenkov.waifupictures.data.api

import com.google.gson.annotations.SerializedName

data class ApiRequest(
    @SerializedName("exclude") val exclude: List<String>
)

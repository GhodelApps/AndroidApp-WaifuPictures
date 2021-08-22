package com.vkochenkov.waifupictures.data.api

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("files") val files: List<String>
)

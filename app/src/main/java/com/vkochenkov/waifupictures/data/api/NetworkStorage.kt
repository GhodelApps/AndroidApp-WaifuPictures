package com.vkochenkov.waifupictures.data.api

import com.vkochenkov.waifupictures.data.api.dto.ApiRequest
import com.vkochenkov.waifupictures.data.model.Category
import kotlin.collections.ArrayList

object NetworkStorage {

    const val defaultType = "sfw"
    var lastChangedCategory: Category = Category.WAIFU
    var excludeRequest: ApiRequest? = ApiRequest(ArrayList())
}
package com.vkochenkov.waifupictures.data.model

import com.vkochenkov.waifupictures.data.api.ApiResponse
import java.util.ArrayList

object Mapper {
    fun map(response: ApiResponse): List<PictureItem> {
        val pictureItemsList = ArrayList<PictureItem>()
        for (file in response.files) {
            val pictureItem = PictureItem(file)
            pictureItemsList.add(pictureItem)
        }
        return pictureItemsList
    }
}
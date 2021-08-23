package com.vkochenkov.waifupictures.presentation.adapter

import com.vkochenkov.waifupictures.data.model.PictureItem

interface PictureItemClickListener {
    fun onItemCLick(holder: PictureViewHolder, item: PictureItem)
}
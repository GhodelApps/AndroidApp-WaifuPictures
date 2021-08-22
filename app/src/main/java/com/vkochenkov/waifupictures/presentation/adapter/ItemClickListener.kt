package com.vkochenkov.waifupictures.presentation.adapter

import com.vkochenkov.waifupictures.data.model.PictureItem


interface ItemClickListener {
    fun onItemCLick(holder: PictureViewHolder, item: PictureItem)
}
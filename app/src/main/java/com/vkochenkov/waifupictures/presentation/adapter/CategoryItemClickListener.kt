package com.vkochenkov.waifupictures.presentation.adapter

import com.vkochenkov.waifupictures.data.model.Category

interface CategoryItemClickListener {
    fun onItemCLick(holder: CategoryViewHolder, item: Category)
}
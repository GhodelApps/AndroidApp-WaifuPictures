package com.vkochenkov.waifupictures.presentation.adapter

interface ItemClickListener<Holder, Item> {
    fun onItemCLick(holder: Holder, item: Item)
}
package com.vkochenkov.waifupictures.presentation.adapter.pictures

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.vkochenkov.waifupictures.data.model.PictureItem
import com.vkochenkov.waifupictures.di.App
import com.vkochenkov.waifupictures.presentation.activity.PictureActivity
import com.vkochenkov.waifupictures.presentation.adapter.ItemClickListener

class PictureItemClickListenerImpl(private val activity: FragmentActivity?) :
    ItemClickListener<PictureViewHolder, PictureItem> {

    override fun onItemCLick(holder: PictureViewHolder, item: PictureItem) {
        val intent = Intent(activity, PictureActivity::class.java).apply {
            putExtra(App.IMAGE_ITEM, item)
        }
        activity?.startActivity(intent)
    }
}
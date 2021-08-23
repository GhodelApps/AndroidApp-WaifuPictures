package com.vkochenkov.waifupictures.presentation.adapter.picture

import android.content.Intent
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentActivity
import com.vkochenkov.waifupictures.R
import com.vkochenkov.waifupictures.data.model.PictureItem
import com.vkochenkov.waifupictures.di.App
import com.vkochenkov.waifupictures.presentation.activity.PictureActivity
import com.vkochenkov.waifupictures.presentation.adapter.ItemClickListener

class PictureItemClickListenerImpl(private val activity: FragmentActivity?) :
    ItemClickListener<PictureViewHolder, PictureItem> {

    override fun onItemCLick(holder: PictureViewHolder, item: PictureItem) {

        val animationWhenPressed =
            AnimationUtils.loadAnimation(activity, R.anim.decreases_when_pressed)

        holder.itemView.startAnimation(animationWhenPressed)

        val intent = Intent(activity, PictureActivity::class.java).apply {
            putExtra(App.IMAGE_ITEM, item)
        }
        activity?.startActivity(intent)
    }
}
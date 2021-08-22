package com.vkochenkov.waifupictures.presentation.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vkochenkov.waifupictures.R
import com.vkochenkov.waifupictures.data.model.PictureItem


class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.iv_item)

    //todo good placeholders
    private val glideOptions: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.ic_baseline_image_24)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    fun bind(item: PictureItem) {
        Glide.with(itemView.context.applicationContext)
            .load(item.largeImageUrl)
            .apply(glideOptions)
            .into(imageView)
    }
}
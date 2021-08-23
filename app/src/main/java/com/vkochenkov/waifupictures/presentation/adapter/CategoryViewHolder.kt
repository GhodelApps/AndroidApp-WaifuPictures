package com.vkochenkov.waifupictures.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.waifupictures.R
import com.vkochenkov.waifupictures.data.model.Category

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textView: TextView = itemView.findViewById(R.id.tv_item)

    fun bind(item: Category) {
        textView.text = item.text
    }
}
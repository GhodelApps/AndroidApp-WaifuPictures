package com.vkochenkov.waifupictures.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.waifupictures.R
import com.vkochenkov.waifupictures.data.model.PictureItem

class PicturesAdapter(private val itemClickListener: ItemClickListener): RecyclerView.Adapter<PictureViewHolder>() {

    private var itemsList: List<PictureItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return PictureViewHolder(view)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val pictureItem = itemsList[position]
        holder.bind(pictureItem)
        holder.itemView.setOnClickListener { itemClickListener.onItemCLick(holder, pictureItem) }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun setItemsList(itemsList: List<PictureItem>) {
        this.itemsList = itemsList
    }
}
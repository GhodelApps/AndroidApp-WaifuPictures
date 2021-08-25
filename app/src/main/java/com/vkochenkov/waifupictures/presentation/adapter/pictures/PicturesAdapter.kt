package com.vkochenkov.waifupictures.presentation.adapter.pictures

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.waifupictures.R
import com.vkochenkov.waifupictures.data.model.PictureItem
import com.vkochenkov.waifupictures.presentation.adapter.ItemClickListener

class PicturesAdapter(
    private val pictureItemClickListener: ItemClickListener<PictureViewHolder, PictureItem>,
    private val reloadClickListener: ItemClickListener<ReloadViewHolder, PictureItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemsList: List<PictureItem> = ArrayList()

    override fun getItemViewType(position: Int): Int {
        return if (position == itemsList.size - 1) {
            1
        } else {
            0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
            PictureViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.reload_item, parent, false)
            ReloadViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val animationWhenPressed = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.decreases_when_pressed
        )

        if (holder is PictureViewHolder) {
            val pictureItem = itemsList[position]
            holder.bind(pictureItem)
            holder.itemView.setOnClickListener {
                holder.itemView.startAnimation(animationWhenPressed)
                pictureItemClickListener.onItemCLick(holder, pictureItem)
            }
        } else if (holder is ReloadViewHolder) {
            val pictureItem = itemsList[position]
            holder.itemView.setOnClickListener {
                holder.itemView.startAnimation(animationWhenPressed)
                reloadClickListener.onItemCLick(holder, pictureItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun setItemsList(itemsList: List<PictureItem>) {
        this.itemsList = itemsList
    }
}
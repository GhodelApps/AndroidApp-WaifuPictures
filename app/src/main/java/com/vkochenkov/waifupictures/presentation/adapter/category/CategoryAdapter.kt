package com.vkochenkov.waifupictures.presentation.adapter.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.waifupictures.R
import com.vkochenkov.waifupictures.data.model.Category
import com.vkochenkov.waifupictures.presentation.adapter.ItemClickListener

class CategoryAdapter(private val categoryItemClickListener: ItemClickListener<CategoryViewHolder, Category>): RecyclerView.Adapter<CategoryViewHolder>() {

    private var itemsList: Array<Category> = Category.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryItem = itemsList[position]
        holder.bind(categoryItem)
        holder.itemView.setOnClickListener {
            categoryItemClickListener.onItemCLick(holder, categoryItem)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}
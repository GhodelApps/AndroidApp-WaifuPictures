package com.vkochenkov.waifupictures.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vkochenkov.waifupictures.R
import com.vkochenkov.waifupictures.data.model.Category
import com.vkochenkov.waifupictures.di.App
import com.vkochenkov.waifupictures.presentation.adapter.CategoryAdapter
import com.vkochenkov.waifupictures.presentation.adapter.CategoryItemClickListener
import com.vkochenkov.waifupictures.presentation.adapter.CategoryViewHolder
import com.vkochenkov.waifupictures.presentation.view_model.PicturesViewModel
import com.vkochenkov.waifupictures.presentation.view_model.ViewModelFactory
import javax.inject.Inject

class ChangeCategoryBottomSheetDialog: BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var categoryRecycler: RecyclerView

    private val picturesViewModel: PicturesViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(PicturesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bottom_sheet_category, container, false)

        categoryRecycler = root.findViewById(R.id.categories_recycler)
        categoryRecycler.layoutManager = LinearLayoutManager(root.context)
        categoryRecycler.adapter = CategoryAdapter(object : CategoryItemClickListener{
            override fun onItemCLick(holder: CategoryViewHolder, item: Category) {
                picturesViewModel.onCategorySelected(item)
            }
        })

        return root
    }
}
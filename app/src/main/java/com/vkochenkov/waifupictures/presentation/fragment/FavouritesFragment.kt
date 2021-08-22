package com.vkochenkov.waifupictures.presentation.fragment

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vkochenkov.waifupictures.R
import com.vkochenkov.waifupictures.data.db.DbState
import com.vkochenkov.waifupictures.data.model.PictureItem
import com.vkochenkov.waifupictures.di.App
import com.vkochenkov.waifupictures.presentation.activity.PictureActivity
import com.vkochenkov.waifupictures.presentation.adapter.PictureViewHolder
import com.vkochenkov.waifupictures.presentation.adapter.PicturesAdapter
import com.vkochenkov.waifupictures.presentation.adapter.ItemClickListener
import com.vkochenkov.waifupictures.presentation.showToast
import com.vkochenkov.waifupictures.presentation.view_model.FavouritesViewModel
import com.vkochenkov.waifupictures.presentation.view_model.ViewModelFactory
import javax.inject.Inject

class FavouritesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var favouritesRecyclerView: RecyclerView
    private lateinit var emptyListTv: TextView
    private lateinit var progressBar: ProgressBar

    private val favouritesViewModel: FavouritesViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(FavouritesViewModel::class.java)
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

        val root = inflater.inflate(R.layout.fragment_favourites, container, false)
        initViews(root)
        initRecyclerView(root)
        initLiveDataObservers()
        return root
    }

    override fun onResume() {
        super.onResume()
        favouritesViewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        favouritesViewModel.firstFirstVisibleRecyclerPosition = (favouritesRecyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (favouritesViewModel.firstFirstVisibleRecyclerPosition != null) {
            (favouritesRecyclerView.layoutManager as GridLayoutManager).scrollToPositionWithOffset(favouritesViewModel.firstFirstVisibleRecyclerPosition as Int,0)
        }
    }

    private fun initViews(view: View) {
        progressBar = view.findViewById(R.id.favourites_progress)
        emptyListTv = view.findViewById(R.id.favourites_empty_tv)
    }

    private fun initLiveDataObservers() {
        favouritesViewModel.dbState.observe(viewLifecycleOwner, Observer {
            emptyListTv.visibility = View.VISIBLE
            when (it) {
                DbState.GETTING -> {
                    progressBar.visibility = View.VISIBLE
                    emptyListTv.visibility = View.INVISIBLE
                }
                DbState.GETTING_ERROR -> {
                    progressBar.visibility = View.INVISIBLE
                    showToast(R.string.load_db_error_text)
                }
                DbState.SUCCESS -> {
                    progressBar.visibility = View.INVISIBLE
                }
            }
        })
        favouritesViewModel.favouritesList.observe(viewLifecycleOwner, Observer {
            (favouritesRecyclerView.adapter as PicturesAdapter).setItemsList(it)
            (favouritesRecyclerView.adapter as PicturesAdapter).notifyDataSetChanged()
            checkItemsListSize()
        })
    }

    private fun checkItemsListSize() {
        if (favouritesViewModel.favouritesList.value?.size == 0 || favouritesViewModel.favouritesList.value == null) {
            emptyListTv.visibility = View.VISIBLE
        } else {
            emptyListTv.visibility = View.INVISIBLE
        }
    }

    private fun initRecyclerView(view: View) {
        favouritesRecyclerView = view.findViewById(R.id.favourites_recycler)
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            favouritesRecyclerView.layoutManager = GridLayoutManager(view.context, 2)
        } else {
            favouritesRecyclerView.layoutManager = GridLayoutManager(view.context, 3)
        }
        favouritesRecyclerView.adapter = PicturesAdapter(object : ItemClickListener {
            override fun onItemCLick(holder: PictureViewHolder, item: PictureItem) {
                val intent = Intent(activity, PictureActivity::class.java).apply {
                    putExtra(App.IMAGE_ITEM, item)
                }
                startActivity(intent)
            }
        })
    }
}
package com.vkochenkov.waifupictures.presentation.fragment

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vkochenkov.waifupictures.R
import com.vkochenkov.waifupictures.data.api.NetworkState
import com.vkochenkov.waifupictures.di.App
import com.vkochenkov.waifupictures.presentation.adapter.PicturesAdapter
import com.vkochenkov.waifupictures.presentation.adapter.PictureItemClickListenerImpl
import com.vkochenkov.waifupictures.presentation.dialog.ChangeCategoryBottomSheetDialog
import com.vkochenkov.waifupictures.presentation.showToast
import com.vkochenkov.waifupictures.presentation.view_model.PicturesViewModel
import com.vkochenkov.waifupictures.presentation.view_model.ViewModelFactory

import javax.inject.Inject

class PicturesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var picturesRecyclerView: RecyclerView
    private lateinit var emptyListTv: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var floatingChangeBtn: FloatingActionButton

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

        picturesViewModel.onCreateView()

        val root = inflater.inflate(R.layout.fragment_pictures, container, false)
        initViews(root)
        initRecyclerView(root)
        initLiveDataObservers()
        setListeners()

        return root
    }

    override fun onPause() {
        super.onPause()
        picturesViewModel.firstFirstVisibleRecyclerPosition = (picturesRecyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (picturesViewModel.firstFirstVisibleRecyclerPosition != null) {
            (picturesRecyclerView.layoutManager as GridLayoutManager).scrollToPositionWithOffset(picturesViewModel.firstFirstVisibleRecyclerPosition as Int,0)
        }
    }

    private fun setListeners() {
        swipeRefreshLayout.setOnRefreshListener {
            picturesViewModel.onSwipeRefresh()
            (picturesRecyclerView.adapter as PicturesAdapter).notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }

        floatingChangeBtn.setOnClickListener{
            val changeCategoryBottomSheetDialog = ChangeCategoryBottomSheetDialog()
            changeCategoryBottomSheetDialog.show(requireActivity().supportFragmentManager, "ChangeCategoryBottomSheetDialog")
        }
    }

    private fun initViews(view: View) {
        progressBar = view.findViewById(R.id.pictures_progress)
        emptyListTv = view.findViewById(R.id.pictures_empty_tv)
        swipeRefreshLayout = view.findViewById(R.id.images_swipe_refresh)
        swipeRefreshLayout.setColorSchemeResources(R.color.teal_200)
        floatingChangeBtn = view.findViewById(R.id.pictures_floating_change_btn)
    }

    private fun initLiveDataObservers() {
        picturesViewModel.networkState.observe(viewLifecycleOwner, Observer {
            when (it) {
                NetworkState.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    emptyListTv.visibility = View.INVISIBLE
                }
                NetworkState.LOADING_ERROR -> {
                    progressBar.visibility = View.INVISIBLE
                    checkItemsListSize()
                    showToast(R.string.load_network_error_text)
                }
                NetworkState.NO_INTERNET_CONNECTION -> {
                    progressBar.visibility = View.INVISIBLE
                    checkItemsListSize()
                    showToast(R.string.no_network_error_text)
                }
                NetworkState.SUCCESS -> {
                    progressBar.visibility = View.INVISIBLE
                    emptyListTv.visibility = View.INVISIBLE
                }
            }
        })
        picturesViewModel.itemsList.observe(viewLifecycleOwner, Observer {
            (picturesRecyclerView.adapter as PicturesAdapter).setItemsList(it)
            (picturesRecyclerView.adapter as PicturesAdapter).notifyDataSetChanged()
        })
    }

    private fun checkItemsListSize() {
        if (picturesViewModel.itemsList.value?.size == 0 || picturesViewModel.itemsList.value == null) {
            emptyListTv.visibility = View.VISIBLE
        } else {
            emptyListTv.visibility = View.INVISIBLE
        }
    }

    private fun initRecyclerView(view: View) {
        picturesRecyclerView = view.findViewById(R.id.pictures_recycler)

        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            picturesRecyclerView.layoutManager = GridLayoutManager(view.context, 2)
        } else {
            picturesRecyclerView.layoutManager = GridLayoutManager(view.context, 3)
        }

        picturesRecyclerView.adapter = PicturesAdapter(PictureItemClickListenerImpl(activity))
    }
}
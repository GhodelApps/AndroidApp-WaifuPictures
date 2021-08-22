package com.vkochenkov.waifupictures.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.waifupictures.data.Repository
import com.vkochenkov.waifupictures.data.db.DbState
import com.vkochenkov.waifupictures.data.model.PictureItem
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _dbState = MutableLiveData<DbState>()
    val dbState: LiveData<DbState> = _dbState

    private val _favouritesList = MutableLiveData<List<PictureItem>>()
    val favouritesList: LiveData<List<PictureItem>> = _favouritesList

    var firstFirstVisibleRecyclerPosition: Int? = null

    fun onResume() {
        getFavourites()
    }

    private fun getFavourites() {
        repository.getAllItemsFromFavorites().subscribe(maybeRxObserver())
    }

    private fun maybeRxObserver() = object : MaybeObserver<List<PictureItem>> {
        override fun onSubscribe(d: Disposable) {
            _dbState.postValue(DbState.GETTING)
        }

        override fun onSuccess(list: List<PictureItem>) {
            _dbState.postValue(DbState.SUCCESS)
            _favouritesList.postValue(list)
        }

        override fun onError(e: Throwable) {
            _dbState.postValue(DbState.GETTING_ERROR)
        }

        override fun onComplete() {
        }
    }
}
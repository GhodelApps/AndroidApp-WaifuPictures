package com.vkochenkov.waifupictures.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.waifupictures.data.Repository
import com.vkochenkov.waifupictures.data.model.PictureItem
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class PictureViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _isFavouriteImage = MutableLiveData<Boolean>()
    val isFavouriteImage: LiveData<Boolean> = _isFavouriteImage

    fun onCreate(item: PictureItem) {
        isImageFavourite(item)
    }

    fun onLikeButtonClick(item: PictureItem) {
        if (_isFavouriteImage.value!!) {
            repository.deleteItemFromFavourites(item).subscribe(changeFavouriteRxObserver(item))
        } else {
            repository.addItemToFavourites(item).subscribe(changeFavouriteRxObserver(item))
        }
    }

    private fun isImageFavourite(item: PictureItem) {
        repository.getItemFromFavourites(item.largeImageUrl).subscribe(itemsRxObserver())
    }

    private fun itemsRxObserver() = object : MaybeObserver<PictureItem> {
        override fun onSubscribe(d: Disposable) {}

        override fun onSuccess(item: PictureItem) {
            _isFavouriteImage.postValue(true)
        }

        override fun onError(e: Throwable) {}

        override fun onComplete() {
            _isFavouriteImage.postValue(false)
        }
    }

    private fun changeFavouriteRxObserver(item: PictureItem) = object : CompletableObserver {
        override fun onSubscribe(d: Disposable) {}

        override fun onError(e: Throwable) {}

        override fun onComplete() {
            isImageFavourite(item)
        }
    }
}
package com.vkochenkov.waifupictures.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.waifupictures.data.Repository
import com.vkochenkov.waifupictures.data.api.NetworkState
import com.vkochenkov.waifupictures.data.api.ApiResponse
import com.vkochenkov.waifupictures.data.model.PictureItem
import com.vkochenkov.waifupictures.data.model.Mapper
import com.vkochenkov.waifupictures.presentation.utils.NetworkChecker
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class PicturesViewModel @Inject constructor(
    private val repository: Repository,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private val _itemsList = MutableLiveData<List<PictureItem>>()
    val itemsList: LiveData<List<PictureItem>> = _itemsList

    var firstFirstVisibleRecyclerPosition: Int? = null

    fun onCreateView() {
        if (_itemsList.value == null) {
            makeApiCall("sfw", "waifu")
        }
    }

    fun onSwipeRefresh() {
        makeApiCall("sfw", "waifu")
    }

    private fun getImagesFromApiRxObserver() = object : SingleObserver<ApiResponse> {
        override fun onSubscribe(d: Disposable) {
            _networkState.postValue(NetworkState.LOADING)
        }

        override fun onSuccess(r: ApiResponse) {
            _networkState.postValue(NetworkState.SUCCESS)
            _itemsList.postValue(Mapper.map(r))
        }

        override fun onError(e: Throwable) {
            _networkState.postValue(NetworkState.LOADING_ERROR)
        }
    }

    private fun makeApiCall(type: String, category: String) {
    //    Log.d("LOGGG", "current page is: ${PaggingStorage.currentPage}")
        if (networkChecker.isOnline()) {
            repository.getPicturesFromApi(type, category).subscribe(getImagesFromApiRxObserver())
        } else {
            _networkState.postValue(NetworkState.NO_INTERNET_CONNECTION)
        }
    }
}
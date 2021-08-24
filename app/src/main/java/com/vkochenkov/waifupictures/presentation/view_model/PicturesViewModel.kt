package com.vkochenkov.waifupictures.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.waifupictures.data.Repository
import com.vkochenkov.waifupictures.data.api.NetworkState
import com.vkochenkov.waifupictures.data.api.NetworkStorage
import com.vkochenkov.waifupictures.data.api.dto.ApiRequest
import com.vkochenkov.waifupictures.data.api.dto.ApiResponse
import com.vkochenkov.waifupictures.data.model.Category
import com.vkochenkov.waifupictures.data.model.Mapper
import com.vkochenkov.waifupictures.data.model.PictureItem
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
            makeApiCall()
        }
    }

    fun onSwipeRefresh() {
        makeApiCall()
    }

    fun onCategorySelected(category: Category) {
        NetworkStorage.lastChangedCategory = category
        makeApiCall()
    }

    private fun getImagesFromApiRxObserver() = object : SingleObserver<ApiResponse> {
        override fun onSubscribe(d: Disposable) {
            _networkState.postValue(NetworkState.LOADING)
        }

        override fun onSuccess(r: ApiResponse) {
            _networkState.postValue(NetworkState.SUCCESS)
            _itemsList.postValue(Mapper.map(r))
            NetworkStorage.excludeRequest = ApiRequest(r.files)
        }

        override fun onError(e: Throwable) {
            _networkState.postValue(NetworkState.LOADING_ERROR)
            NetworkStorage.excludeRequest = ApiRequest(ArrayList())
        }
    }

    private fun makeApiCall() {
        firstFirstVisibleRecyclerPosition = 0

        if (networkChecker.isOnline()) {
            repository.getPicturesFromApi(
                NetworkStorage.defaultType,
                NetworkStorage.lastChangedCategory.text.toLowerCase(),
                NetworkStorage.excludeRequest
            ).subscribe(getImagesFromApiRxObserver())
        } else {
            _networkState.postValue(NetworkState.NO_INTERNET_CONNECTION)
        }
    }
}
package com.vkochenkov.waifupictures.data

import com.vkochenkov.waifupictures.data.api.dto.ApiRequest
import com.vkochenkov.waifupictures.data.api.ApiService
import com.vkochenkov.waifupictures.data.db.FavouriteImagesDao
import com.vkochenkov.waifupictures.data.model.PictureItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val dao: FavouriteImagesDao
) {

    fun getPicturesFromApi(type: String, category: String, body: ApiRequest?) =
        apiService.getAllImages(type, category, body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getAllItemsFromFavorites() =
        dao.getImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getItemFromFavourites(url: String) =
        dao.getImage(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun deleteItemFromFavourites(item: PictureItem) =
        dao.deleteImage(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun addItemToFavourites(item: PictureItem) =
        dao.insertImage(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
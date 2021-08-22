package com.vkochenkov.waifupictures.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vkochenkov.waifupictures.data.Repository
import com.vkochenkov.waifupictures.presentation.utils.NetworkChecker
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: Repository,
                                           private val networkChecker: NetworkChecker
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PicturesViewModel::class.java) -> {
                PicturesViewModel(repository, networkChecker) as T
            }
            modelClass.isAssignableFrom(FavouritesViewModel::class.java) -> {
                FavouritesViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PictureViewModel::class.java) -> {
                PictureViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
package com.vkochenkov.waifupictures.di.component


import com.vkochenkov.waifupictures.data.Repository
import com.vkochenkov.waifupictures.di.module.AppModule
import com.vkochenkov.waifupictures.di.module.DatabaseModule
import com.vkochenkov.waifupictures.di.module.NetworkModule
import com.vkochenkov.waifupictures.presentation.activity.PictureActivity
import com.vkochenkov.waifupictures.presentation.activity.MainActivity
import com.vkochenkov.waifupictures.presentation.fragment.AppInfoFragment
import com.vkochenkov.waifupictures.presentation.fragment.FavouritesFragment
import com.vkochenkov.waifupictures.presentation.fragment.PicturesFragment
import com.vkochenkov.waifupictures.presentation.utils.ImageLoader
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class, DatabaseModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: PictureActivity)

    fun inject(fragment: PicturesFragment)
    fun inject(fragment: FavouritesFragment)
    fun inject(fragment: AppInfoFragment)

    fun inject(repository: Repository)
    fun inject(imageLoader: ImageLoader)
}
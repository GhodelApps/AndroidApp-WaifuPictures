package com.vkochenkov.waifupictures.di.module

import android.app.Application
import com.vkochenkov.waifupictures.data.Repository
import com.vkochenkov.waifupictures.data.api.ApiService
import com.vkochenkov.waifupictures.data.db.FavouriteImagesDao
import com.vkochenkov.waifupictures.di.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideAppContext(): Application = app

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, dao: FavouriteImagesDao): Repository = Repository(apiService, dao)
}
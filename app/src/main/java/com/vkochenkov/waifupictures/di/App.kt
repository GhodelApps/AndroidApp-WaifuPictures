package com.vkochenkov.waifupictures.di

import android.app.Application
import com.vkochenkov.waifupictures.di.component.AppComponent
import com.vkochenkov.waifupictures.di.component.DaggerAppComponent
import com.vkochenkov.waifupictures.di.module.AppModule
import com.vkochenkov.waifupictures.di.module.DatabaseModule
import com.vkochenkov.waifupictures.di.module.NetworkModule

class App: Application() {

    companion object {
        lateinit var appComponent: AppComponent
        //constants
        const val IMAGE_ITEM = "IMAGE_ITEM"
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .databaseModule(DatabaseModule())
            .build()
    }
}
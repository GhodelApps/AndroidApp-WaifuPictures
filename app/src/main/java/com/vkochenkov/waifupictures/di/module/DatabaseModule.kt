package com.vkochenkov.waifupictures.di.module

import android.app.Application
import androidx.room.Room
import com.vkochenkov.waifupictures.data.db.FavouriteImagesDao
import com.vkochenkov.waifupictures.data.db.FavouriteImagesDatabase
import com.vkochenkov.waifupictures.data.db.FavouriteImagesDatabase.Companion.dbName
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Application): FavouriteImagesDatabase {
        return Room.databaseBuilder(context, FavouriteImagesDatabase::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesDao(databaseFavourite: FavouriteImagesDatabase): FavouriteImagesDao {
        return databaseFavourite.imagesDao()
    }
}
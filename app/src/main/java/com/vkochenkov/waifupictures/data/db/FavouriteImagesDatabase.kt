package com.vkochenkov.waifupictures.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vkochenkov.waifupictures.data.model.PictureItem

@Database(entities = [(PictureItem::class)], version = 3)
abstract class FavouriteImagesDatabase : RoomDatabase() {

    companion object {
        const val dbName = "favourite_pictures_db"
    }

    abstract fun imagesDao() : FavouriteImagesDao
}
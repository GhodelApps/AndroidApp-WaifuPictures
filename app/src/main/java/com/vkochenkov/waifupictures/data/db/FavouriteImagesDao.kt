package com.vkochenkov.waifupictures.data.db

import androidx.room.*
import com.vkochenkov.waifupictures.data.model.PictureItem
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface FavouriteImagesDao {

    @Query("SELECT * FROM Pictures")
    fun getImages(): Maybe<List<PictureItem>>

    @Query("SELECT * FROM Pictures WHERE largeImageUrl = :url")
    fun getImage(url: String): Maybe<PictureItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertImage(item: PictureItem): Completable

    @Delete()
    fun deleteImage(item: PictureItem): Completable
}
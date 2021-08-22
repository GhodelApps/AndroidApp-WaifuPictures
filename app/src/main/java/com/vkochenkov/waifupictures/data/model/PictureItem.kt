package com.vkochenkov.waifupictures.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Pictures")
data class PictureItem(
    @PrimaryKey val largeImageUrl: String,
) : Parcelable


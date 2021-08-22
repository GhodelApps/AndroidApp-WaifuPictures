package com.vkochenkov.waifupictures.data.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    companion object {
        const val BASE_URL = "https://api.waifu.pics/"
    }

    @POST("many/{type}/{category}")
    fun getAllImages(
        @Path("type") type: String,
        @Path("category") category: String,
        @Body obj: Object = Object()
    ): Single<ApiResponse>
}
package com.vkochenkov.waifupictures.data.api

import com.vkochenkov.waifupictures.data.api.dto.ApiRequest
import com.vkochenkov.waifupictures.data.api.dto.ApiResponse
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
        @Body body: ApiRequest?
    ): Single<ApiResponse>
}
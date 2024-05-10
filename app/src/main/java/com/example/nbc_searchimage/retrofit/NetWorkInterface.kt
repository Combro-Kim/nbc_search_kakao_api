package com.example.nbc_searchimage.retrofit

import com.example.nbc_searchimage.BuildConfig
import com.example.nbc_searchimage.data.DocumentResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface NetWorkInterface {
    @Headers("Authorization: ${ BuildConfig.API_KEY }")
    @GET("image")
    suspend fun getImageResponse(
        @Query("query") query:String,
        @Query("size") size:Int = 80,
        @Query("sort") sort:String = "recency"
    ) : DocumentResponse
}
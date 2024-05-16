package com.example.nbc_searchimage.data.remote

import com.example.nbc_searchimage.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface SearchRemoteDataSource {
    @Headers("Authorization: KakaoAK a1f5c532c747be7eb01ade357afef92a")
    @GET("v2/search/image")
    suspend fun getDocumentResponse(
        @Query("query") query:String,
        @Query("size") size:Int = 80,
        @Query("sort") sort:String = "recency"
    ) : DocumentResponse
}
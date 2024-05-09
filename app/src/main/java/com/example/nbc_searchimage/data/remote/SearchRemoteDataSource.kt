package com.example.nbc_searchimage.data.remote

import com.example.nbc_searchimage.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface SearchRemoteDataSource {
    @Headers("Authorization: ${BuildConfig.API_KEY}")
    @GET("v2/search/")
    suspend fun getDocumentResponse(
        @Query("query") query:String,
        @Query("size") size:Int = 80,
        @Query("sort") sort:String = "recency"
    ) : DocumentResponse
    // : DustResponse
}
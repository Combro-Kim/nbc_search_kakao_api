package com.example.nbc_searchimage.network

import com.example.nbc_searchimage.BuildConfig
import com.example.nbc_searchimage.data.remote.SearchRemoteDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// retrofit을 사용하기 위한 세팅
object RetrofitClient {

    private const val SEARCH_BASE_URL = "https://dapi.kakao.com/"

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        //요청, 응답 정보 기록
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    //자바 객체 < - > JSON 변환
    private val searchRetrofit = Retrofit.Builder()
        .baseUrl(SEARCH_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()

    val searchNetWork: SearchRemoteDataSource = searchRetrofit.create(SearchRemoteDataSource::class.java)
}
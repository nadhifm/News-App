package com.example.core.data.sources.remote.network

import com.example.core.BuildConfig
import com.example.core.data.sources.remote.response.ListArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val APIKEY = BuildConfig.APIKEY

interface ArticleApiService {
    @GET("top-headlines")
    suspend fun getAllArticle(
        @Query("country")
        countryCode: String = "us",
        @Query("q")
        query: String,
        @Query("apiKey")
        apiKey: String = APIKEY,
        @Query("pageSize")
        pageSize: Int = 50,
    ): ListArticleResponse
}
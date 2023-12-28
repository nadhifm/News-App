package com.example.core.data.sources.remote

import com.example.core.data.sources.remote.network.ArticleApiService
import com.example.core.data.sources.remote.response.ArticleResponse
import javax.inject.Inject

class ArticleRemoteDataSourceImpl @Inject constructor(
    private val apiService: ArticleApiService
) : ArticleRemoteDataSource {
    override suspend fun getAllArticle(query: String): List<ArticleResponse> {
        try {
            val result = apiService.getAllArticle(query = query)
            return result.articles
        } catch (e: Exception) {
            throw e
        }
    }
}
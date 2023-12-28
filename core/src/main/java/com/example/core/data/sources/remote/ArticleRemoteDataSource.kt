package com.example.core.data.sources.remote

import com.example.core.data.sources.remote.response.ArticleResponse

interface ArticleRemoteDataSource {
    suspend fun getAllArticle(query: String): List<ArticleResponse>
}
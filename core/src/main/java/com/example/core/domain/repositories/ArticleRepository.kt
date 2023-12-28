package com.example.core.domain.repositories

import com.example.core.domain.models.Article
import com.example.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getAllArticle(query: String): Flow<Resource<List<Article>>>
    fun getBookmarkArticle(): Flow<List<Article>>
    fun checkBookmarkArticle(title: String): Flow<Boolean>
    suspend fun addBookmarkArticle(article: Article)
    suspend fun removeBookmarkArticle(title: String)
}
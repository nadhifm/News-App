package com.example.core.data.sources.local

import com.example.core.data.sources.local.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticleLocalDataSource {
    fun getAllArticle(): Flow<List<ArticleEntity>>
    fun getArticleByTitle(title: String): Flow<ArticleEntity>
    suspend fun addArticle(articleEntity: ArticleEntity)
    suspend fun removeArticle(title: String)
}
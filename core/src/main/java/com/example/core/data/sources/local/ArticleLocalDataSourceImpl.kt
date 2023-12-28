package com.example.core.data.sources.local

import com.example.core.data.sources.local.database.ArticleDao
import com.example.core.data.sources.local.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleLocalDataSourceImpl @Inject constructor(
    private val articleDao: ArticleDao,
) : ArticleLocalDataSource {
    override fun getAllArticle(): Flow<List<ArticleEntity>> = articleDao.getAllArticle()

    override fun getArticleByTitle(title: String): Flow<ArticleEntity> {
        return articleDao.getArticleByTitle(title)
    }

    override suspend fun addArticle(articleEntity: ArticleEntity) =
        articleDao.insertArticle(articleEntity)

    override suspend fun removeArticle(title: String) = articleDao.deleteArticle(title)
}
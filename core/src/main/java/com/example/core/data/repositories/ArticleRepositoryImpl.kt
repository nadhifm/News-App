package com.example.core.data.repositories

import com.example.core.data.mapper.toDomain
import com.example.core.data.mapper.toEntity
import com.example.core.data.sources.local.ArticleLocalDataSource
import com.example.core.data.sources.remote.ArticleRemoteDataSource
import com.example.core.domain.models.Article
import com.example.core.domain.repositories.ArticleRepository
import com.example.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val remoteDataSource: ArticleRemoteDataSource,
    private val localDataSource: ArticleLocalDataSource,
) : ArticleRepository {
    override fun getAllArticle(query: String): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading())
        try {
            val result = remoteDataSource.getAllArticle(query)
            emit(Resource.Success(result.filter {
                it.urlToImage != null && it.url != null && it.description != null
            }.map { it.toDomain() }))
        } catch (e: Exception) {
            e.message?.let {
                emit(Resource.Error(it))
            } ?: kotlin.run {
                emit(Resource.Error("An unexpected error occurred"))
            }
        }
    }

    override fun getBookmarkArticle(): Flow<List<Article>> = localDataSource.getAllArticle().map {
        it.map { articleEntity ->
            articleEntity.toDomain()
        }
    }

    override fun checkBookmarkArticle(title: String): Flow<Boolean> = flow {
        try {
            val favoriteArticle = localDataSource.getArticleByTitle(title).firstOrNull()
            if (favoriteArticle != null) {
                emit(true)
            } else {
                emit(false)
            }
        } catch (e: Exception) {
            emit(false)
        }
    }

    override suspend fun addBookmarkArticle(article: Article) =
        localDataSource.addArticle(article.toEntity())

    override suspend fun removeBookmarkArticle(title: String) = localDataSource.removeArticle(title)
}
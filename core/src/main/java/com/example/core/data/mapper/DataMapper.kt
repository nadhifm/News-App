package com.example.core.data.mapper

import com.example.core.data.sources.local.entities.ArticleEntity
import com.example.core.data.sources.remote.response.ArticleResponse
import com.example.core.domain.models.Article

fun ArticleResponse.toDomain(): Article = Article(
    title = title,
    publishedAt = publishedAt ?: "",
    author = author ?: "",
    sourceName = source.name,
    urlToImage = urlToImage ?: "",
    url = url ?: "",
    description = description ?: "",
)

fun ArticleEntity.toDomain(): Article = Article(
    title = title,
    publishedAt = publishedAt,
    author = author,
    sourceName = sourceName,
    urlToImage = urlToImage,
    url = url,
    description = description,
)

fun Article.toEntity(): ArticleEntity = ArticleEntity(
    title = title,
    publishedAt = publishedAt,
    author = author,
    sourceName = sourceName,
    urlToImage = urlToImage,
    url = url,
    description = description,
)
package com.example.core.domain.usecases

import com.example.core.domain.models.Article
import com.example.core.domain.repositories.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkArticle @Inject constructor(
    private val repository: ArticleRepository
) {
    operator fun invoke(): Flow<List<Article>> = repository.getBookmarkArticle()
}
package com.example.core.domain.usecases

import com.example.core.domain.models.Article
import com.example.core.domain.repositories.ArticleRepository
import javax.inject.Inject

class AddFavoriteArticle @Inject constructor(
    private val repository: ArticleRepository,
) {
    suspend fun invoke(article: Article) = repository.addBookmarkArticle(article)
}
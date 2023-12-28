package com.example.core.domain.usecases

import com.example.core.domain.repositories.ArticleRepository
import javax.inject.Inject

class RemoveFavoriteArticle @Inject constructor(
    private val repository: ArticleRepository,
) {
    suspend fun invoke(title: String) = repository.removeBookmarkArticle(title)
}
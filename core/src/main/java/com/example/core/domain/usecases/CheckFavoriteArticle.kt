package com.example.core.domain.usecases

import com.example.core.domain.repositories.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckFavoriteArticle @Inject constructor(
    private val repository: ArticleRepository,
) {
    operator fun invoke(title: String): Flow<Boolean> = repository.checkBookmarkArticle(title)
}
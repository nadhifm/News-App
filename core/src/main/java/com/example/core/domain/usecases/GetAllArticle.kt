package com.example.core.domain.usecases

import com.example.core.domain.models.Article
import com.example.core.domain.repositories.ArticleRepository
import com.example.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllArticle @Inject constructor(
    private val repository: ArticleRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<Article>>> =
        repository.getAllArticle(query)
}
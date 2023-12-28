package com.example.core.di

import com.example.core.domain.repositories.ArticleRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DaggerDependencies {
    fun providesArticleRepository(): ArticleRepository
}
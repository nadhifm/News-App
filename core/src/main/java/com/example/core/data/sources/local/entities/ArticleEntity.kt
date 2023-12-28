package com.example.core.data.sources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val author: String,
    val publishedAt: String,
    val urlToImage: String,
    val sourceName: String,
    val url: String,
    val description: String,
)
package com.example.core.data.sources.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ListArticleResponse(
    @field:SerializedName("articles")
    val articles: List<ArticleResponse>,
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("totalResults")
    val totalResults: Int
)
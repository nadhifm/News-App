package com.example.core.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String,
    val publishedAt: String,
    val author: String = "",
    val sourceName: String = "",
    val urlToImage: String = "",
    val url: String,
    val description: String,
) : Parcelable

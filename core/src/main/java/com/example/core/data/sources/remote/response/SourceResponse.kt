package com.example.core.data.sources.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SourceResponse(
    @field:SerializedName("id")
    val id: Any? = null,
    @field:SerializedName("name")
    val name: String
)

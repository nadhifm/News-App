package com.example.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    fun formatDate(dateString: String): String {
        val originalDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val originalDate: Date? = originalDateFormat.parse(dateString)

        val targetDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        originalDate?.let {
            return targetDateFormat.format(originalDate)
        } ?: kotlin.run {
            return dateString
        }
    }
}
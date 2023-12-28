package com.example.core.data.sources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.sources.local.entities.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "news_db"
    }
}
package com.example.core.di

import android.app.Application
import androidx.room.Room
import com.example.core.BuildConfig
import com.example.core.data.repositories.ArticleRepositoryImpl
import com.example.core.data.sources.local.ArticleLocalDataSource
import com.example.core.data.sources.local.ArticleLocalDataSourceImpl
import com.example.core.data.sources.local.database.AppDatabase
import com.example.core.data.sources.remote.ArticleRemoteDataSource
import com.example.core.data.sources.remote.ArticleRemoteDataSourceImpl
import com.example.core.data.sources.remote.network.ArticleApiService
import com.example.core.domain.repositories.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val BASEURL = BuildConfig.BASEURL
const val PARAPHRASE = BuildConfig.PARAPHRASE

@InstallIn(SingletonComponent::class)
@Module
object CoreModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): AppDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(PARAPHRASE.toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val hostname = "newsapi.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/3zBmqXH8RxlLbD8WUZQIcgaUohZ0QxprXZrYvFGqWko=")
            .add(hostname, "sha256/hS5jJ4P+iQUErBkvoWBQOd1T7VOAYlOVegvv1iMzpxA=")
            .add(hostname, "sha256/A+L5NEZLzX9+Tzc2Y5TMTKjdRlaasLKndpTU0hrW6jI=")
            .add(hostname, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
            .build()
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
                .build()
        } else {
            OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideArticleApiService(retrofit: Retrofit): ArticleApiService =
        retrofit.create(ArticleApiService::class.java)

    @Provides
    @Singleton
    fun provideArticleRemoteDataSource(apiService: ArticleApiService): ArticleRemoteDataSource {
        return ArticleRemoteDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideArticleLocalDataSource(database: AppDatabase): ArticleLocalDataSource {
        return ArticleLocalDataSourceImpl(database.articleDao())
    }

    @Provides
    @Singleton
    fun provideArticleRepository(
        remoteDataSource: ArticleRemoteDataSource,
        localDataSource: ArticleLocalDataSource,
    ): ArticleRepository {
        return ArticleRepositoryImpl(remoteDataSource, localDataSource)
    }
}
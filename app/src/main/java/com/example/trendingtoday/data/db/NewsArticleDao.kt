package com.example.trendingtoday.data.db

import androidx.room.*
import com.example.trendingtoday.data.model.NewsArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsArticle(newsArticle: NewsArticle)

    @Query("SELECT * FROM news_articles")
    fun getSavedNewsArticles(): Flow<List<NewsArticle>>

    @Delete
    suspend fun deleteSavedArticle(newsArticle: NewsArticle)
}
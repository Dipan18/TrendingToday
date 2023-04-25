package com.example.trendingtoday.data.repository.datasource

import com.example.trendingtoday.data.model.NewsArticle
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveNewsArticleToDB(newsArticle: NewsArticle)
    fun getSavedNewsArticles(): Flow<List<NewsArticle>>
    suspend fun deleteSavedArticle(newsArticle: NewsArticle)
}
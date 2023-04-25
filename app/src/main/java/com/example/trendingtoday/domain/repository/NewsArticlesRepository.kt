package com.example.trendingtoday.domain.repository

import com.example.trendingtoday.data.model.NewsArticle
import com.example.trendingtoday.data.model.NewsArticleListResponse
import com.example.trendingtoday.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsArticlesRepository {
    suspend fun getNewsArticles(country: String, pageNo: Int): Resource<NewsArticleListResponse>
    suspend fun getNewsArticleSearchResults(
        searchQuery: String,
        pageNo: Int
    ): Resource<NewsArticleListResponse>

    suspend fun saveNewsArticle(newsArticle: NewsArticle)
    suspend fun deleteNewsArticle(newsArticle: NewsArticle)
    fun getSavedNewsArticles(): Flow<List<NewsArticle>>
}
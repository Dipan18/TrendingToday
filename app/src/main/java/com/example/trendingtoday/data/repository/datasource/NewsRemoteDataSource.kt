package com.example.trendingtoday.data.repository.datasource

import com.example.trendingtoday.data.model.NewsArticleListResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopNewsArticlesFromAPI(
        country: String,
        pageNo: Int
    ): Response<NewsArticleListResponse>

    suspend fun getSearchResultsForQueryFromAPI(
        query: String,
        pageNo: Int
    ): Response<NewsArticleListResponse>
}
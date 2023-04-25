package com.example.trendingtoday.data.api

import com.example.trendingtoday.data.model.NewsArticleListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {
    @GET("/v2/top-headlines")
    suspend fun getTopNewsArticles(
        @Query("country") country: String,
        @Query("page") pageNo: Int
    ): Response<NewsArticleListResponse>

    @GET("/v2/top-headlines")
    suspend fun getSearchResultsForQuery(
        @Query("q") query: String,
        @Query("page") pageNo: Int
    ): Response<NewsArticleListResponse>
}
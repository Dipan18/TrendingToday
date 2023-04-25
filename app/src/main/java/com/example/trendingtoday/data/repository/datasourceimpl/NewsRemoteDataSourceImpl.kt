package com.example.trendingtoday.data.repository.datasourceimpl

import com.example.trendingtoday.data.api.NewsAPIService
import com.example.trendingtoday.data.model.NewsArticleListResponse
import com.example.trendingtoday.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService,
) : NewsRemoteDataSource {
    override suspend fun getTopNewsArticlesFromAPI(
        country: String,
        pageNo: Int
    ): Response<NewsArticleListResponse> = newsAPIService.getTopNewsArticles(country, pageNo)

    override suspend fun getSearchResultsForQueryFromAPI(
        query: String,
        pageNo: Int
    ): Response<NewsArticleListResponse> = newsAPIService.getSearchResultsForQuery(query, pageNo)
}
package com.example.trendingtoday.data.repository

import com.example.trendingtoday.data.model.NewsArticle
import com.example.trendingtoday.data.model.NewsArticleListResponse
import com.example.trendingtoday.data.repository.datasource.NewsLocalDataSource
import com.example.trendingtoday.data.repository.datasource.NewsRemoteDataSource
import com.example.trendingtoday.data.util.Resource
import com.example.trendingtoday.domain.repository.NewsArticlesRepository
import retrofit2.Response

class NewsArticlesRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsArticlesRepository {
    private fun responseToResource(response: Response<NewsArticleListResponse>): Resource<NewsArticleListResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(response.message())
    }

    override suspend fun getNewsArticles(
        country: String,
        pageNo: Int
    ): Resource<NewsArticleListResponse> =
        responseToResource(newsRemoteDataSource.getTopNewsArticlesFromAPI(country, pageNo))

    override suspend fun getNewsArticleSearchResults(
        searchQuery: String,
        pageNo: Int
    ): Resource<NewsArticleListResponse> = responseToResource(
        newsRemoteDataSource.getSearchResultsForQueryFromAPI(
            searchQuery,
            pageNo
        )
    )

    override suspend fun saveNewsArticle(newsArticle: NewsArticle) =
        newsLocalDataSource.saveNewsArticleToDB(newsArticle)

    override suspend fun deleteNewsArticle(newsArticle: NewsArticle) =
        newsLocalDataSource.deleteSavedArticle(newsArticle)

    override fun getSavedNewsArticles() =
        newsLocalDataSource.getSavedNewsArticles()
}
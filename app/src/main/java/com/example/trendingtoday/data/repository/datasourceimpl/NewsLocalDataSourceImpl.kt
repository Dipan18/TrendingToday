package com.example.trendingtoday.data.repository.datasourceimpl

import com.example.trendingtoday.data.db.NewsArticleDao
import com.example.trendingtoday.data.model.NewsArticle
import com.example.trendingtoday.data.repository.datasource.NewsLocalDataSource

class NewsLocalDataSourceImpl(
    private val newsArticleDao: NewsArticleDao
) : NewsLocalDataSource {
    override suspend fun saveNewsArticleToDB(newsArticle: NewsArticle) =
        newsArticleDao.insertNewsArticle(newsArticle)

    override fun getSavedNewsArticles() =
        newsArticleDao.getSavedNewsArticles()

    override suspend fun deleteSavedArticle(newsArticle: NewsArticle) =
        newsArticleDao.deleteSavedArticle(newsArticle)
}
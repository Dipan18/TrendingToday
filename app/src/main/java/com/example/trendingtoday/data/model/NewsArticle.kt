package com.example.trendingtoday.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "news_articles")
data class NewsArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: Publisher,
    val title: String,
    val url: String,
    val urlToImage: String?
) : Serializable
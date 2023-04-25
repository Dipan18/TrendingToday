package com.example.trendingtoday.presentation

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.trendingtoday.data.model.NewsArticle
import com.example.trendingtoday.data.model.NewsArticleListResponse
import com.example.trendingtoday.data.util.Resource
import com.example.trendingtoday.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val context: Application,
    private val getNewsArticlesUseCase: GetNewsArticlesUseCase,
    private val getSearchNewsArticlesUseCase: SearchNewsArticlesUseCase,
    private val saveNewsArticlesUseCase: SaveNewsArticlesUseCase,
    private val getSavedNewsArticlesUseCase: GetSavedNewsArticlesUseCase,
    private val deleteSavedNewsArticlesUseCase: DeleteSavedNewsArticlesUseCase
) : AndroidViewModel(context) {
    private val _newsArticles = MutableLiveData<Resource<NewsArticleListResponse>>()
    val newsArticles: LiveData<Resource<NewsArticleListResponse>> = _newsArticles

    private val _searchResults = MutableLiveData<Resource<NewsArticleListResponse>>()
    val searchResults: LiveData<Resource<NewsArticleListResponse>> = _searchResults

    fun getTopNewsArticles(country: String, pageNo: Int) = viewModelScope.launch(Dispatchers.IO) {
        val oldNewsArticles = _newsArticles.value?.data?.articles ?: emptyList()

        _newsArticles.postValue(Resource.Loading())
        try {
            if (!isInternetAvailable()) {
                _newsArticles.postValue(Resource.Error("Internet Not Available!"))
                return@launch
            }

            _newsArticles.postValue(Resource.Loading())

            val articles = getNewsArticlesUseCase.execute(country, pageNo)
            val newNewsArticles = articles.data?.articles ?: emptyList()
            val combined = oldNewsArticles + newNewsArticles
            articles.data?.articles = combined
            _newsArticles.postValue(articles)
        } catch (exception: Exception) {
            _newsArticles.postValue(Resource.Error(exception.message.toString()))
        }
    }

    fun getSearchResultsForQuery(query: String, pageNo: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isInternetAvailable()) {
                    return@launch
                }

                _searchResults.postValue(Resource.Loading())

                val searchResults = getSearchNewsArticlesUseCase.execute(query, pageNo)
                _searchResults.postValue(searchResults)
            } catch (exception: Exception) {
                _searchResults.postValue(Resource.Error(exception.message.toString()))
            }
        }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.run {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
        return result
    }

    fun saveNewsArticleInDB(newsArticle: NewsArticle) = viewModelScope.launch(Dispatchers.IO) {
        saveNewsArticlesUseCase.execute(newsArticle)
    }

    fun getSavedNewsArticles() = liveData {
        getSavedNewsArticlesUseCase.execute().collect {
            emit(it)
        }
    }

    fun deleteSavedNewsArticle(newsArticle: NewsArticle) = viewModelScope.launch(Dispatchers.IO) {
        deleteSavedNewsArticlesUseCase.execute(newsArticle)
    }
}
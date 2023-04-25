package com.example.trendingtoday.data.api

import com.example.trendingtoday.BuildConfig
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIServiceTest {
    private lateinit var server: MockWebServer
    private lateinit var service: NewsAPIService

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .client(OkHttpClient().newBuilder().addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("X-Api-Key", BuildConfig.API_KEY)
                chain.proceed(builder.build())
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    private fun enqueueMockResponse(filename: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(filename)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadlines_sentRequest_receivedNotNullResponse() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopNewsArticles("ca", 1).body()
            assertThat(responseBody).isNotNull()
        }
    }

    @Test
    fun getTopHeadlines_sentRequest_correctUrl() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            service.getTopNewsArticles("ca", 1).body()
            val sentRequest = server.takeRequest()
            val correctUrl = "/v2/top-headlines?country=ca&page=1"
            assertThat(sentRequest.path).isEqualTo(correctUrl)
        }
    }

    @Test
    fun getTopHeadlines_sentRequest_correctResultsPerPage() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopNewsArticles("ca", 1).body()
            val resultsPerPage = 20
            assertThat(responseBody!!.articles.size).isEqualTo(resultsPerPage)
        }
    }

    @Test
    fun getTopHeadlines_sentRequest_receivedCorrectResponse() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopNewsArticles("ca", 1).body()
            val article = responseBody?.articles?.get(0)
            val authorNameOfFirstArticleInResponse = "Ali Zaslav, Morgan Rimmer, Ted Barrett"
            assertThat(article?.author).isEqualTo(authorNameOfFirstArticleInResponse)
        }
    }

    @Test
    fun retrofitInstance_sentRequest_hasApiKeyInHeader() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            service.getTopNewsArticles("ca", 1).body()
            val request = server.takeRequest()
            val headerName = "X-Api-Key"
            assertThat(request.headers.names()).contains(headerName)
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}
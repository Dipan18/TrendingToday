package com.example.trendingtoday.presentation.di

import com.example.trendingtoday.BuildConfig
import com.example.trendingtoday.data.api.NewsAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient().newBuilder().addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("X-Api-Key", BuildConfig.API_KEY)
                chain.proceed(builder.build())
            }.build())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsAPIService(retrofit: Retrofit): NewsAPIService =
        retrofit.create(NewsAPIService::class.java)
}
package com.example.newsapi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {
    @GET("/v2/top-headlines/")
    suspend fun getDataFromApiCountry(
        @Query("country") country: String = "in",
        @Query("apiKey") apiKey: String = "ff08d3141b4048ed94a9c379762829f6",
        @Query("q") q: String = "",
    ): Response<NewsResponse>

    @GET("/v2/top-headlines/")
    suspend fun getDataFromApiSources(
        @Query("sources") sources: String = "",
        @Query("apiKey") apiKey: String = "ff08d3141b4048ed94a9c379762829f6"

    ): Response<NewsResponse>
}
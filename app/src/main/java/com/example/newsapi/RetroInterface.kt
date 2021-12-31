package com.example.newsapi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {
    @GET("/v2/top-headlines/")
    suspend fun getDataFromApi(
        @Query("country") country: String = "in",
        @Query("apiKey") apiKey: String = "ff08d3141b4048ed94a9c379762829f6"
    ): Response<NewsResponse>
}
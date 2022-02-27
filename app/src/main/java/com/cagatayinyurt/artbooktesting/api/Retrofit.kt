package com.cagatayinyurt.artbooktesting.api

import com.cagatayinyurt.artbooktesting.model.ImageResponse
import com.cagatayinyurt.artbooktesting.util.Util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Retrofit {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = API_KEY
    ) : Response<ImageResponse>
}
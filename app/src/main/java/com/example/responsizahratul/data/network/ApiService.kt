package com.example.responsizahratul.data.network

import com.example.responsizahratul.data.model.GameDetailResponse
import com.example.responsizahratul.data.model.GameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun getGames(
        @Query("search") search: String? = null,
        @Query("page_size") pageSize: Int? = null
    ): GameResponse

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: Int
    ): GameDetailResponse
}
